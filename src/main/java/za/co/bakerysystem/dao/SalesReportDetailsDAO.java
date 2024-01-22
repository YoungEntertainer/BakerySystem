package za.co.bakerysystem.dao;

import java.util.List;
import za.co.bakerysystem.model.SalesReportDetails;

public interface SalesReportDetailsDAO {

    void createSaleDetail(SalesReportDetails salesReportDetails);

    void updateSaleDetail(SalesReportDetails salesReportDetails);

    List<SalesReportDetails> getSalesDetails(int saleID);

    void deleteSaleDetail(int reportID);
}
