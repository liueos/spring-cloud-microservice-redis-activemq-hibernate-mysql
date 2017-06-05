package com.hzg.erp;

import com.google.gson.reflect.TypeToken;
import com.hzg.tools.SignInUtil;
import com.hzg.tools.Writer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.*;

@Controller
@RequestMapping("/erp")
public class ErpController {

    Logger logger = Logger.getLogger(ErpController.class);

    @Autowired
    private ProductDao sysDao;

    @Autowired
    private Writer writer;

    /**
     * 保存实体
     * @param response
     * @param entity
     * @param json
     */
    @Transactional
    @PostMapping("/save")
    public void save(HttpServletResponse response, String entity, @RequestBody String json){
        logger.info("save start, parameter:" + entity + ":" + json);

        String result = "fail";
        Timestamp inputDate = new Timestamp(System.currentTimeMillis());

        if (entity.equalsIgnoreCase(Purchase.class.getSimpleName())) {
            Purchase purchase = writer.gson.fromJson(json, Purchase.class);
            purchase.setInputDate(inputDate);
            result = sysDao.save(purchase);

            if (purchase.getDetails() != null) {
                for (PurchaseDetail detail : purchase.getDetails()) {
                    detail.setPurchase(purchase);
                    sysDao.save(detail);
                }
            }

        } else if (entity.equalsIgnoreCase(Supplier.class.getSimpleName())) {
            Supplier supplier = writer.gson.fromJson(json, Supplier.class);
            supplier.setInputDate(inputDate);
            result = sysDao.save(supplier);

        } else if (entity.equalsIgnoreCase(Product.class.getSimpleName())) {
            Product product = writer.gson.fromJson(json, Product.class);
            result = sysDao.save(product);

        } else if (entity.equalsIgnoreCase(ProductType.class.getSimpleName())) {
            ProductType productType = writer.gson.fromJson(json, ProductType.class);
            result = sysDao.save(productType);
        }

        writer.writeStringToJson(response, "{\"result\":\"" + result + "\"}");
        logger.info("save end, result:" + result);
    }

    @Transactional
    @PostMapping("/update")
    public void update(HttpServletResponse response, String entity, @RequestBody String json){
        logger.info("update start, parameter:" + entity + ":" + json);

        String result = "fail";

        if (entity.equalsIgnoreCase(Purchase.class.getSimpleName())) {
            Purchase purchase = writer.gson.fromJson(json, Purchase.class);
            result = sysDao.updateById(purchase.getId(), purchase);

            if (purchase.getDetails() != null) {
                for (PurchaseDetail detail : purchase.getDetails()) {
                    sysDao.updateById(detail.getId(), detail);
                }
            }

        } else if (entity.equalsIgnoreCase(Supplier.class.getSimpleName())) {
            Supplier supplier = writer.gson.fromJson(json, Supplier.class);
            result = sysDao.updateById(supplier.getId(), supplier);

        } else if (entity.equalsIgnoreCase(Product.class.getSimpleName())) {
            Product product = writer.gson.fromJson(json, Product.class);
            result = sysDao.updateById(product.getId(), product);

        } else if (entity.equalsIgnoreCase(ProductType.class.getSimpleName())) {
            ProductType productType = writer.gson.fromJson(json, ProductType.class);
            result = sysDao.updateById(productType.getId(), productType);
        }

        writer.writeStringToJson(response, "{\"result\":\"" + result + "\"}");
        logger.info("update end, result:" + result);
    }

    @RequestMapping(value = "/query", method = {RequestMethod.GET, RequestMethod.POST})
    public void query(HttpServletResponse response, String entity, @RequestBody String json){
        logger.info("query start, parameter:" + entity + ":" + json);

        if (entity.equalsIgnoreCase(Purchase.class.getSimpleName())) {
            writer.writeObjectToJson(response, sysDao.query(writer.gson.fromJson(json, Purchase.class)));

        } else if (entity.equalsIgnoreCase(Supplier.class.getSimpleName())) {
            writer.writeObjectToJson(response, sysDao.query(writer.gson.fromJson(json, Supplier.class)));

        } else if (entity.equalsIgnoreCase(Product.class.getSimpleName())) {
            writer.writeObjectToJson(response, sysDao.query(writer.gson.fromJson(json, Product.class)));

        } else if (entity.equalsIgnoreCase(ProductType.class.getSimpleName())) {
            writer.writeObjectToJson(response, sysDao.query(writer.gson.fromJson(json, ProductType.class)));
        }

        logger.info("query end");
    }

    @RequestMapping(value = "/suggest", method = {RequestMethod.GET, RequestMethod.POST})
    public void suggest(HttpServletResponse response, String entity, @RequestBody String json){
        logger.info("suggest start, parameter:" + entity + ":" + json);

        if (entity.equalsIgnoreCase(Purchase.class.getSimpleName())) {
            Purchase purchase = writer.gson.fromJson(json, Purchase.class);
            purchase.setState(0);

            Field[] limitFields = new Field[1];
            try {
                limitFields[0] = purchase.getClass().getDeclaredField("state");
            } catch (Exception e) {
                e.printStackTrace();
            }

            writer.writeObjectToJson(response, sysDao.suggest(purchase, limitFields));

        } else if (entity.equalsIgnoreCase(Supplier.class.getSimpleName())) {
            Supplier supplier = writer.gson.fromJson(json, Supplier.class);
            supplier.setState(0);

            Field[] limitFields = new Field[1];
            try {
                limitFields[0] = supplier.getClass().getDeclaredField("state");
            } catch (Exception e) {
                e.printStackTrace();
            }

            writer.writeObjectToJson(response, sysDao.suggest(supplier, limitFields));

        } else if (entity.equalsIgnoreCase(Product.class.getSimpleName())) {
            Product product = writer.gson.fromJson(json, Product.class);
            writer.writeObjectToJson(response, sysDao.suggest(product, null));

        } else if (entity.equalsIgnoreCase(ProductType.class.getSimpleName())) {
            ProductType productType = writer.gson.fromJson(json, ProductType.class);
            writer.writeObjectToJson(response, sysDao.suggest(productType, null));
        }

        logger.info("suggest end");
    }

    @RequestMapping(value = "/complexQuery", method = {RequestMethod.GET, RequestMethod.POST})
    public void complexQuery(HttpServletResponse response, String entity, @RequestBody String json, int position, int rowNum){
        logger.info("complexQuery start, parameter:" + entity + ":" + json + "," + position + "," + rowNum);

        Map<String, String> queryParameters = writer.gson.fromJson(json, new TypeToken<Map<String, String>>(){}.getType());

        if (entity.equalsIgnoreCase(Purchase.class.getSimpleName())) {
            writer.writeObjectToJson(response, sysDao.complexQuery(Purchase.class, queryParameters, position, rowNum));

        } else if (entity.equalsIgnoreCase(Supplier.class.getSimpleName())) {
            writer.writeObjectToJson(response, sysDao.complexQuery(Supplier.class, queryParameters, position, rowNum));

        } else if (entity.equalsIgnoreCase(Product.class.getSimpleName())) {
            writer.writeObjectToJson(response, sysDao.complexQuery(Product.class, queryParameters, position, rowNum));

        } else if (entity.equalsIgnoreCase(ProductType.class.getSimpleName())) {
            writer.writeObjectToJson(response, sysDao.complexQuery(ProductType.class, queryParameters, position, rowNum));
        }

        logger.info("complexQuery end");
    }

    /**
     * 查询条件限制下的记录数
     * @param response
     * @param entity
     * @param json
     */
    @RequestMapping(value = "/recordsSum", method = {RequestMethod.GET, RequestMethod.POST})
    public void recordsSum(HttpServletResponse response, String entity, @RequestBody String json){
        logger.info("recordsSum start, parameter:" + entity + ":" + json);
        BigInteger recordsSum = new BigInteger("-1");

        Map<String, String> queryParameters = writer.gson.fromJson(json, new TypeToken<Map<String, String>>(){}.getType());

        if (entity.equalsIgnoreCase(Purchase.class.getSimpleName())) {
            recordsSum =  sysDao.recordsSum(Purchase.class, queryParameters);

        } else if (entity.equalsIgnoreCase(Supplier.class.getSimpleName())) {
            recordsSum =  sysDao.recordsSum(Supplier.class, queryParameters);

        } else if (entity.equalsIgnoreCase(Product.class.getSimpleName())) {
            recordsSum =  sysDao.recordsSum(Product.class, queryParameters);

        } else if (entity.equalsIgnoreCase(ProductType.class.getSimpleName())) {
            recordsSum =  sysDao.recordsSum(ProductType.class, queryParameters);
        }

        writer.writeStringToJson(response, "{\"recordsSum\":" + recordsSum + "}");

        logger.info("recordsSum end");
    }
}