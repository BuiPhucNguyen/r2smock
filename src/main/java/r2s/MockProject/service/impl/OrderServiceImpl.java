package r2s.MockProject.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import jakarta.transaction.Transactional;
import r2s.MockProject.entity.Account;
import r2s.MockProject.entity.Order;
import r2s.MockProject.entity.OrderDetail;
import r2s.MockProject.entity.Product;
import r2s.MockProject.enums.ErrorCodeEnum;
import r2s.MockProject.enums.OrderStatusEnum;
import r2s.MockProject.model.ActionResult;
import r2s.MockProject.model.dto.OrderDetailInDto;
import r2s.MockProject.model.dto.OrderInDto;
import r2s.MockProject.model.dto.OrderOutDto;
import r2s.MockProject.model.entity.OrderModel;
import r2s.MockProject.repository.AccountRepository;
import r2s.MockProject.repository.OrderRepository;
import r2s.MockProject.repository.ProductReponsitory;
import r2s.MockProject.service.OrderService;
import r2s.MockProject.utils.CurrentUserUtils;
import r2s.MockProject.utils.ExportUtils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.stream.Collectors;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private ProductReponsitory productReponsitory;

	@Override
	public ActionResult getAllOrders(Integer page, Integer size) {
		ActionResult result = new ActionResult();
		Page<Order> ordersPage = orderRepository.findAll((PageRequest.of(page - 1, size)));
		if (ordersPage.isEmpty()) {
			result.setErrorCodeEnum(ErrorCodeEnum.NO_CONTENT);
			return result;
		}

		List<OrderModel> orderModels = ordersPage.stream().map(OrderModel::transform).collect(Collectors.toList());

		if (orderModels.isEmpty()) {
			result.setErrorCodeEnum(ErrorCodeEnum.NO_CONTENT);
			return result;
		}
		OrderOutDto OutDto = new OrderOutDto();

		OutDto.setOrders(orderModels);
		OutDto.setTotal(orderModels.size());
		result.setData(OutDto);
		return result;
	}

	@Override
	public ActionResult createOrder(OrderInDto orderIn) {
		ActionResult result = new ActionResult();

		Account account = accountRepository.findByUsername(CurrentUserUtils.getCurrentUsernames());

		if (account == null) {
			result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
			return result;
		}

		Order order = new Order();
		order.setAccount(account);
		order.setAddress(orderIn.getAddress());
		order.setPhone(orderIn.getPhone());
		order.setCreatedDate(new Date());

		order.setShipPrice(orderIn.getShipPrice());
		order.setNote(orderIn.getNote());
		order.setStatus(OrderStatusEnum.PENDING.getStatus());

		List<OrderDetailInDto> dtos = orderIn.getOrderDetails();

		BigDecimal orderDetailsPrice = new BigDecimal(0);

		List<OrderDetail> details = new ArrayList<>();
		for (OrderDetailInDto orderDetailInDto : dtos) {
			OrderDetail orderDetail = new OrderDetail();
			Product product = productReponsitory.getProductById(orderDetailInDto.getProductId());

			if (product == null || product.getStatus().equals(false)) {
				result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
				return result;
			}

			if (product.getStock() < orderDetailInDto.getAmount()) {
				result.setErrorCodeEnum(ErrorCodeEnum.NO_ENOUGH_PRODUCT_STOCK);

				if (result.getErrorCodeEnum() != ErrorCodeEnum.OK) {
					TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
				}
				return result;
			} else {
				product.setStock(product.getStock() - orderDetailInDto.getAmount());
				productReponsitory.save(product);
			}

			orderDetail.setOrder(order);
			orderDetail.setProduct(product);
			orderDetail.setAmount(orderDetailInDto.getAmount());

			BigDecimal a = BigDecimal.valueOf(orderDetailInDto.getAmount());
			BigDecimal p = product.getPrice();
			BigDecimal pur = p.multiply(a);
			orderDetail.setPurchasePrice(pur);

			orderDetailsPrice = orderDetailsPrice.add(pur);

			details.add(orderDetail);

		}
		order.setOrderDetails(details);

		order.setOrderDetailsPrice(orderDetailsPrice);
		order.setTotalPrice(orderDetailsPrice.add(orderIn.getShipPrice()));

		Order orderTemp = orderRepository.save(order);
		result.setData(OrderModel.transform(orderTemp));
		return result;
	}

	@Override
	public ActionResult findOrderById(Integer id) {
		ActionResult result = new ActionResult();
		Order order = orderRepository.getOrderById(id);
		if (order == null) {
			result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
			return result;
		}

		result.setData(OrderModel.transform(order));
		return result;
	}

	@Override
	public ActionResult findOrderByAccountCurrent(Integer page, Integer size) {
		ActionResult result = new ActionResult();

		Account account = accountRepository.findByUsername(CurrentUserUtils.getCurrentUsernames());

		Page<Order> pageResult = orderRepository.getOrderByAccountId(account.getId(), PageRequest.of(page - 1, size));

		if (pageResult.getNumberOfElements() == 0) {
			result.setErrorCodeEnum(ErrorCodeEnum.NO_CONTENT);
			return result;
		}

		List<OrderModel> orderModels = pageResult.get().map(OrderModel::transform).collect(Collectors.toList());

		OrderOutDto outDto = new OrderOutDto();
		outDto.setOrders(orderModels);
		outDto.setTotal(pageResult.getNumberOfElements());

		result.setData(outDto);

		return result;
	}

	@Override
	public ActionResult updateStatusCompleteOrder(Integer id) {
		ActionResult result = new ActionResult();
		Order order = orderRepository.getOrderById(id);

		if (order == null) {
			result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
			return result;
		}

		if (order.getStatus().equalsIgnoreCase(OrderStatusEnum.CANCEL.getStatus())) {
			result.setErrorCodeEnum(ErrorCodeEnum.CANT_COMPLETE_CANCELED_ODER);
			return result;
		}

		order.setStatus(OrderStatusEnum.COMPLETE.getStatus());

		List<OrderDetail> details = order.getOrderDetails();
		for (OrderDetail orderDetail : details) {
			Product product = orderDetail.getProduct();
			product.setSold(product.getSold() + orderDetail.getAmount());
			productReponsitory.save(product);
		}

		result.setData(OrderModel.transform(order));
		return result;
	}

	@Override
	public ActionResult updateStatusCancelOrder(Integer id) {
		ActionResult result = new ActionResult();
		Order order = orderRepository.getOrderById(id);

		if (order == null) {
			result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
			return result;
		}

		if (order.getStatus().equalsIgnoreCase(OrderStatusEnum.COMPLETE.getStatus())) {
			result.setErrorCodeEnum(ErrorCodeEnum.CANT_CANCEL_COMPLETED_ODER);
			return result;
		}

		order.setStatus(OrderStatusEnum.CANCEL.getStatus());

		List<OrderDetail> details = order.getOrderDetails();
		for (OrderDetail orderDetail : details) {
			Product product = orderDetail.getProduct();
			product.setStock(product.getStock() + orderDetail.getAmount());
			productReponsitory.save(product);
		}

		result.setData(OrderModel.transform(order));
		return result;
	}

	@Override
	public ActionResult exportOrder(List<Integer> orderIds, String filePath) throws IOException {
		ActionResult result = new ActionResult();

		Workbook workbook = ExportUtils.getWorkbook(filePath);

		Sheet sheet = workbook.createSheet("Orders");

		int rowIndex = 0;

		// Write header
		writeHeader(sheet, rowIndex);

		// Write data
		List<Order> orders = new ArrayList<>();

		for (Integer id : orderIds) {
			Order order = orderRepository.getOrderById(id);
			if (order != null) {
				orders.add(order);
			} else {
				result.setErrorCodeEnum(ErrorCodeEnum.INVALID_ENTITY);
				return result;
			}
		}

		rowIndex++;
		for (Order order : orders) {
			// Create row
			Row row = sheet.createRow(rowIndex);
			// Write data on row
			writeOrder(order, row);
			rowIndex++;
		}

		// Auto resize column witdth
		int numberOfColumn = sheet.getRow(0).getPhysicalNumberOfCells();
		autosizeColumn(sheet, numberOfColumn);

		// Create file excel
		ExportUtils.createOutputFile(workbook, filePath);

		result.setData(new String("Export success to " + filePath));
		return result;
	}

	// -------------------------------export orders---------------------------------------------

	// Write header with format
	private static void writeHeader(Sheet sheet, int rowIndex) {
		// Create row
		Row row = sheet.createRow(rowIndex);

		// Create cells
		Cell cell = row.createCell(0);
		cell.setCellValue("Id");

		cell = row.createCell(1);
		cell.setCellValue("Username");

		cell = row.createCell(2);
		cell.setCellValue("Full name");

		cell = row.createCell(3);
		cell.setCellValue("Address");

		cell = row.createCell(4);
		cell.setCellValue("Phone");

		cell = row.createCell(5);
		cell.setCellValue("Created Date");

		cell = row.createCell(6);
		cell.setCellValue("Amount Product");

		cell = row.createCell(7);
		cell.setCellValue("Total Product Price");

		cell = row.createCell(8);
		cell.setCellValue("Ship Price");

		cell = row.createCell(9);
		cell.setCellValue("Final Price");

		cell = row.createCell(10);
		cell.setCellValue("Note");

		cell = row.createCell(11);
		cell.setCellValue("Status");
	}

	// Write data
	private static void writeOrder(Order order, Row row) {
		Cell cell = row.createCell(0);
		cell.setCellValue(order.getId());

		cell = row.createCell(1);
		cell.setCellValue(order.getAccount().getUserName());

		cell = row.createCell(2);
		cell.setCellValue(order.getAccount().getFirstName() + " " + order.getAccount().getLastName());

		cell = row.createCell(3);
		cell.setCellValue(order.getAddress());

		cell = row.createCell(4);
		cell.setCellValue(order.getPhone());

		cell = row.createCell(5);
		cell.setCellValue(order.getCreatedDate().toString());

		List<OrderDetail> details = order.getOrderDetails();
		int amount = 0;
		for (OrderDetail orderDetail : details) {
			amount += orderDetail.getAmount();
		}

		cell = row.createCell(6);
		cell.setCellValue(amount);

		cell = row.createCell(7);
		cell.setCellValue(String.valueOf(order.getOrderDetailsPrice()));

		cell = row.createCell(8);
		cell.setCellValue(String.valueOf(order.getShipPrice()));
		
		cell = row.createCell(9);
		cell.setCellValue(String.valueOf(order.getTotalPrice()));

		cell = row.createCell(10);
		cell.setCellValue(order.getNote());

		cell = row.createCell(11);
		cell.setCellValue(order.getStatus());
	}

	// Auto resize column width
	private static void autosizeColumn(Sheet sheet, int lastColumn) {
		for (int columnIndex = 0; columnIndex < lastColumn; columnIndex++) {
			sheet.autoSizeColumn(columnIndex);
		}
	}
	// -----------------------------------------------------------------------------------------
}
