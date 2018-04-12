package cn.acyou.iblog.utility;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author youfang
 * @date 2018-04-12 下午 09:52
 **/
public class ResultInfo<T> implements java.io.Serializable {
        private static final long serialVersionUID = 12864L;
        private int code;//ajax操作结果
        private String param = "";//ajax操作完成返回对象pk值
        private String str = "";//ajax回调函数执行条件或者是回调消息
        private String btnStatus = "";//按钮状态
        private String token = "";
        private List<T> list;
        /**
         * 额外信息，自定义信息都可以放在里面
         */
        private Object data;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getParam() {
            return param;
        }

        public void setParam(String param) {
            this.param = param;
        }

        public String getStr() {
            return str;
        }

        public void setStr(String str) {
            this.str = str;
        }

        public String getBtnStatus() {
            return btnStatus;
        }

        public void setBtnStatus(String btnStatus) {
            this.btnStatus = btnStatus;
        }

        public ResultInfo() {

        }

        public ResultInfo(int code) {
            this.code = code;
        }

        public ResultInfo(String status, String param, String str, String btnStatus) {
            super();
            this.param = param;
            this.str = str;
            this.btnStatus = btnStatus;
        }

        public List<T> getList() {
            return list;
        }

        public void setList(List<T> list) {
            this.list = list;
        }

        private String idBill;
        private String noBill;
        private String idStock;
        private String noStock;
        private String idPay;
        private String noPay;


        public String getIdBill() {
            return idBill;
        }

        public void setIdBill(String idBill) {
            this.idBill = idBill;
        }

        public String getNoBill() {
            return noBill;
        }

        public void setNoBill(String noBill) {
            this.noBill = noBill;
        }

        public String getIdStock() {
            return idStock;
        }

        public void setIdStock(String idStock) {
            this.idStock = idStock;
        }

        public String getNoStock() {
            return noStock;
        }

        public void setNoStock(String noStock) {
            this.noStock = noStock;
        }

        public String getIdPay() {
            return idPay;
        }

        public void setIdPay(String idPay) {
            this.idPay = idPay;
        }

        public String getNoPay() {
            return noPay;
        }

        public void setNoPay(String noPay) {
            this.noPay = noPay;
        }

        public Object getData() {
            return data;
        }

        public void setData(Object data) {
            this.data = data;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
}
