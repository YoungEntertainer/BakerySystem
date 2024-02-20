package za.co.bakerysystem.dao;

import java.util.List;
import za.co.bakerysystem.model.SalesReportDetails;

public interface SalesReportDetailsDAO {

    boolean createSaleDetail(SalesReportDetails salesReportDetails);

    boolean updateSaleDetail(SalesReportDetails salesReportDetails);

    List<SalesReportDetails> getSalesDetails(int saleID);

    boolean deleteSaleDetail(int reportID);
}
