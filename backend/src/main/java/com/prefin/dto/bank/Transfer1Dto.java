package com.prefin.dto.bank;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transfer1Dto {
    private AccountInfoDto.DataHeader dataHeader;
    private AccountInfoDto.DataBody dataBody;

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
        @JsonProperty("입금은행코드")
        private String bankCode;

        @JsonProperty("입금계좌번호")
        private String accountNumber;

    }

}
