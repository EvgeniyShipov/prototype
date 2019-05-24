package ru.alfastrah.prototype;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

@Data
public class Agent {
    private static final SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
    private Date startDate;
    private String FIO;
    private Status status;
    private BigDecimal value;
    private String phone;
    private BigDecimal loanKV = new BigDecimal(Math.round(Math.random() * 10_000));
    private final BigDecimal sumValue = new BigDecimal(Math.round(Math.random() * 1000_000));
    private String statusOk = Math.round(Math.random() * 3) % 2 == 0 ? "Все хорошо" : "Обратить внимание";

    public String getStringStartDate(){
        return format.format(startDate);
    }

    @AllArgsConstructor
    public enum Status {
        NEW("Новый"),
        DOC_COMPLETE("Подтвержден комплект документов"),
        COMPLETE("Оформление документов завершено");

        private String caption;
        @Override
        public String toString(){
            return caption;
        }
    }

}
