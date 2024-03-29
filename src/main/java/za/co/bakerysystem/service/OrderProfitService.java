package za.co.bakerysystem.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface OrderProfitService {

    List<Map<String, Object>> fetchOrderProfit();

    List<Map<String, Object>> fetchOrderProfitLastMonth();

    List<Map<String, Object>> fetchSaleProfit();

    List<Map<String, Object>> fetchSaleProfitLastMonth();

    List<Map<String, Object>> fetchOrderProfitInRange(LocalDate startDate, LocalDate endDate);

    List<Map<String, Object>> fetchSaleProfitInRange(LocalDate startDate, LocalDate endDate);
}
