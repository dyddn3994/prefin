package com.prefin.component;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalTime;

public class TransactionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LocalTime currentTime = LocalTime.now();
        LocalTime stopTimeStart = LocalTime.of(1, 57);
        LocalTime stopTimeEnd = LocalTime.of(1, 57);

        // 거래 중지 시간에 요청이 들어오면 처리를 중단
        if (currentTime.isAfter(stopTimeStart) && currentTime.isBefore(stopTimeEnd)) {
            response.getWriter().write("거래 중지 중입니다.");
            return false;
        }

        // 거래 중지 시간이 아니면 요청을 처리
        return true;
    }
}