package com.hzg.tools;

public class ErpConstant {

    public final static Integer purchase_state_apply = 0;
    public final static Integer purchase_state_close = 1;
    public final static Integer purchase_state_cancel = 2;

    public final static Integer product_state_purchase = 0;
    public final static Integer product_state_purchase_pass = 10;
    public final static Integer product_state_purchase_close = 11;
    public final static Integer product_state_stockIn = 1;
    public final static Integer product_state_stockOut = 2;
    public final static Integer product_state_onSale = 3;
    public final static Integer product_state_sold = 4;
    public final static Integer product_state_invalid = 5;
    public final static Integer product_state_edit = 6;
    public final static Integer product_state_mediaFiles_uploaded = 7;

    public final static String no_purchase_perfix = "CG";
    public final static String no_stockInOut_perfix = "RC";
    public final static String no_stock_perfix = "KC";
    public final static String no_warehouse_perfix = "CK";

    public final static Integer stockInOut_type_increment = 2;
    public final static Integer stockInOut_type_deposit = 4;
    public final static Integer stockInOut_type_processRepair = 5;
    public final static Integer stockInOut_type_outWarehouse = 10;

    public final static Integer stockInOut_state_apply = 0;
    public final static Integer stockInOut_state_finished = 1;
    public final static Integer stockInOut_state_cancel = 2;

    public final static Integer stock_state_valid = 0;
    public final static Integer stock_state_invalid = 1;

    public final static String no_stockOut_perfix = "CK";
    public final static Integer stockInOut_type_normal_outWarehouse = 11;

    public final static String unit_g = "克";
    public final static String unit_kg = "千克";
    public final static String unit_ct = "克拉";
    public final static String unit_oz = "盎司";
}
