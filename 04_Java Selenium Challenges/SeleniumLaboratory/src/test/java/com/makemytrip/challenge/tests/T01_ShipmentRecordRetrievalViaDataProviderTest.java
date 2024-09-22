package com.makemytrip.challenge.tests;

import com.makemytrip.util.ExcelReader;
import org.assertj.core.api.Assertions;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class T01_ShipmentRecordRetrievalViaDataProviderTest {

    @DataProvider(name = "shipmentData")
    public Iterator<Object[]> getTestData() {
        List<Object[]> testData = ExcelReader
                .getDataFromExcel("src/test/resources/testData/shipment-records.xlsx", "Sheet1");
        return testData.iterator();
    }

    @Test(dataProvider = "shipmentData", dataProviderClass = T01_ShipmentRecordRetrievalViaDataProviderTest.class)
    public void tc001_getAllShipmentRecordsTest(String shipmentId, String courierCompany, String amount, String deliveryDate) {
        Assertions.assertThat(shipmentId)
                .withFailMessage("'ShipmentId' is empty, i.e. no records found")
                .isNotEmpty();

        System.out.println(shipmentId + " | " + courierCompany + " | " + amount + " | " + deliveryDate);
    }

    @Test
    public void tc002_getDetailsByShipmentIdTest() {
        String shipmentId = "ZHW98712";
        Map<String, String> shipmentDetails = ExcelReader
                .getDataFromExcelByColValue("src/test/resources/testData/shipment-records.xlsx",
                        "Sheet4", shipmentId);

        Assertions.assertThat(shipmentDetails)
                .withFailMessage("Details for shipmentId '" + shipmentId + "' not found")
                .isNotEmpty();

        System.out.println(shipmentDetails);
    }
}
