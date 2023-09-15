package com.prefin.dto.bank;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AccountInfoDto {
    private DataHeader dataHeader;
    private DataBody dataBody;

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class DataHeader {
        private String successCode;
        private String resultCode;
        private String resultMessage;
    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class DataBody {
        @JsonProperty("계좌번호")
        private String accountNumber;

        @JsonProperty("상품명")
        private String productName;

        @JsonProperty("계좌잔액")
        private String balance;

        @JsonProperty("고객명")
        private String customerName;

        @JsonProperty("거래내역반복횟수")
        private String transactionRetry;

        @JsonProperty("거래내역")
        private List<TransactionHistory> histories;

    }

    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class TransactionHistory {
        @JsonProperty("거래일자")
        private String transactionDate;

        @JsonProperty("거래시간")
        private String transactionTime;

        @JsonProperty("적요")
        private String briefs;

        @JsonProperty("출금금액")
        private String withdraw;

        @JsonProperty("입금금액")
        private String deposit;

        @JsonProperty("내용")
        private String content;

        @JsonProperty("잔액")
        private String balance;

        @JsonProperty("입지구분")
        private String locationClassification;

        @JsonProperty("거래점명")
        private String transactionPoint;

    }
}
