package za.co.bakerysystem.service.impl;

import za.co.bakerysystem.dao.OrderProfitDAO;
import za.co.bakerysystem.service.OrderProfitService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import za.co.bakerysystem.dao.impl.OrderProfitDAOImpl;

public class OrderProfitServiceImpl implements OrderProfitService {

    private OrderProfitDAO orderProfitDAO;

    public OrderProfitServiceImpl(OrderProfitDAO orderProfitDAO) {
        this.orderProfitDAO = orderProfitDAO;
    }

    @Override
    public List<Map<String, Object>> fetchOrderProfit() {
        return orderProfitDAO.fetchOrderProfit();
    }

    @Override
    public List<Map<String, Object>> fetchOrderProfitLastMonth() {
        return orderProfitDAO.fetchOrderProfitLastMonth();
    }

    @Override
    public List<Map<String, Object>> fetchSaleProfit() {
        return orderProfitDAO.fetchSaleProfit();
    }

    @Override
    public List<Map<String, Object>> fetchSaleProfitLastMonth() {
        return orderProfitDAO.fetchSaleProfitLastMonth();
    }

    @Override
    public List<Map<String, Object>> fetchOrderProfitInRange(LocalDate startDate, LocalDate endDate) {
        return orderProfitDAO.fetchOrderProfitInRange(startDate, endDate);
    }

    @Override
    public List<Map<String, Object>> fetchSaleProfitInRange(LocalDate startDate, LocalDate endDate) {
        return orderProfitDAO.fetchSaleProfitInRange(startDate, endDate);
    }
    
    //----------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------
    //----------------------------------------------------------------------------------------------------
    
      public static void main(String[] args) {
        OrderProfitDAO orderProfitDAO = new OrderProfitDAOImpl();
        OrderProfitService orderProfitService = new OrderProfitServiceImpl(orderProfitDAO);

        // Test fetchOrderProfit
//        List<Map<String, Object>> orderProfitData = orderProfitService.fetchOrderProfit();
//        printResult(orderProfitData);

//        // Test fetchOrderProfitLastMonth
//        List<Map<String, Object>> orderProfitLastMonthData = orderProfitService.fetchOrderProfitLastMonth();
//        System.out.println("Order Profit Last Month Data: " + orderProfitLastMonthData);

//        // Test fetchSaleProfit
//        List<Map<String, Object>> saleProfitData = orderProfitService.fetchSaleProfit();
//        System.out.println("Sale Profit Data: " + saleProfitData);

//        // Test fetchSaleProfitLastMonth
//        List<Map<String, Object>> saleProfitLastMonthData = orderProfitService.fetchSaleProfitLastMonth();
//        System.out.println("Sale Profit Last Month Data: " + saleProfitLastMonthData);

        // Test fetchOrderProfitInRange
        LocalDate startDate = LocalDate.of(2024, 1, 1);
        LocalDate endDate = LocalDate.of(2025, 1, 31);
        List<Map<String, Object>> orderProfitInRangeData = orderProfitService.fetchOrderProfitInRange(startDate, endDate);
        System.out.println("Order Profit In Range Data: " + orderProfitInRangeData);

//        // Test fetchSaleProfitInRange
//        List<Map<String, Object>> saleProfitInRangeData = orderProfitService.fetchSaleProfitInRange(startDate, endDate);
//        System.out.println("Sale Profit In Range Data: " + saleProfitInRangeData);


    }
      
        private static void printResult(List<Map<String, Object>> resultList) {
        for (Map<String, Object> row : resultList) {
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
            System.out.println("----------");
        }
    }
}
