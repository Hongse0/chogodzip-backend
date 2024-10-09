package com.kb.room.mapper;

import com.kb._config.RootConfig;
import com.kb._config.ServletConfig;
import com.kb._config.WebConfig;
import com.kb._config.WebMvcConfig;
import com.kb.room.dto.request.BasicInfo;
import com.kb.room.dto.request.GosiwonPostDTO;
import com.kb.room.dto.request.RoomTempPostDTO;
import com.kb.room.vo.Gosiwon;
import com.kb.room.vo.RoomTemp;
import com.kb.room.vo.RoomWithLoan;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.math.BigDecimal;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@WebAppConfiguration
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RootConfig.class, WebConfig.class, WebMvcConfig.class, ServletConfig.class})
class RoomMapperTest {
    @Autowired private RoomMapper mapper;

    @Test
    @DisplayName("ROOM 작성")
    void saveRoom() {
        RoomTemp room = RoomTemp.builder()
                .userId(2L)
                .roomLat(new BigDecimal("37.0996527684079"))
                .roomLong(new BigDecimal("126.839982282494"))
                .thumbnail("https://image.neoflat.net/vgTmOpt30n25GpYp_-4_SqXjBDE=/240x288/filters:no_upscale():watermark(/resource/gobang.png,center,center,0,20,none)/house/2760/abf4686a-658e-4324-82a0-ff62863003a6.jpg")
                .canLoan(false)
                .build();

        mapper.saveRoom(room);
        Assertions.assertEquals(14L, room.getRoomId());
    }

    @Test
    @DisplayName("[대출 없음] 고시원 작성")
    void saveGosiwon() {
        RoomTemp room = RoomTemp.builder()
                .userId(1L)
                .roomLat(new BigDecimal("37.0996527684079"))
                .roomLong(new BigDecimal("126.839982282494"))
                .thumbnail("https://image.neoflat.net/vgTmOpt30n25GpYp_-4_SqXjBDE=/240x288/filters:no_upscale():watermark(/resource/gobang.png,center,center,0,20,none)/house/2760/abf4686a-658e-4324-82a0-ff62863003a6.jpg")
                .canLoan(false)
                .build();

        mapper.saveRoom(room);

        Gosiwon gosiwon = Gosiwon.builder()
                .room(room)
                .category("gosiwon")
                .title("고시원 제목1")
                .postcode("06903")
                .address("서울특별시 관악구 서림 7길")
                .detailAddress("99 4층")
                .priceMin(40)
                .priceMax(65)
                .depositMin(10)
                .depositMax(15)
                .maintenanceFee(0)
                .privateFacilities("개인화장실|개인샤워실")
                .services("creditCard|freeMeal")
                .languages("eng")
                .desc("상세 설명....")
                .genderLimit(0)
                .type(0)
                .contractMin(1)
                .ageMin(0)
                .ageMax(0)
                .facilityHeating("center")
                .facilityCooling("center")
                .facilityLife("desk|chair")
                .facilitySecurity("CCTV")
                .buildingType(0)
                .canParking(0)
                .hasElevator(0)
                .isSoldOut(false)
                .build();

        mapper.saveGosiwon(gosiwon);
        Assertions.assertEquals(4L, gosiwon.getGswId());
    }

    @Test
    @DisplayName("[대출 가능] 고시원 작성")
    void saveGosiwonWithLoan() {
        //부동산
        RoomTemp room = RoomTemp.builder()
                .userId(1L)
                .roomLat(new BigDecimal("37.0996527684079"))
                .roomLong(new BigDecimal("126.839982282494"))
                .thumbnail("https://image.neoflat.net/vgTmOpt30n25GpYp_-4_SqXjBDE=/240x288/filters:no_upscale():watermark(/resource/gobang.png,center,center,0,20,none)/house/2760/abf4686a-658e-4324-82a0-ff62863003a6.jpg")
                .canLoan(true)
                .build();

        mapper.saveRoom(room);
        Assertions.assertEquals(18L, room.getRoomId());

        //고시원
        Gosiwon gosiwon = Gosiwon.builder()
                .room(room)
                .category("gosiwon")
                .title("고시원 제목2 - 대출 가능")
                .postcode("06903")
                .address("서울특별시 관악구 서림 7길")
                .detailAddress("99 4층")
                .priceMin(40)
                .priceMax(65)
                .depositMin(10)
                .depositMax(15)
                .maintenanceFee(0)
                .privateFacilities("개인화장실|개인샤워실")
                .services("creditCard|freeMeal")
                .languages("eng")
                .desc("상세 설명....")
                .genderLimit(0)
                .type(0)
                .contractMin(1)
                .ageMin(0)
                .ageMax(0)
                .facilityHeating("center")
                .facilityCooling("center")
                .facilityLife("desk|chair")
                .facilitySecurity("CCTV")
                .buildingType(0)
                .canParking(0)
                .hasElevator(0)
                .isSoldOut(false)
                .build();

        mapper.saveGosiwon(gosiwon);
        Assertions.assertEquals(6L, gosiwon.getGswId());

        //대출
        if(room.getCanLoan()) {
            RoomWithLoan rwl = RoomWithLoan.builder()
                    .roomId(room.getRoomId())
                    .loanId(1L) //버팀목
                    .build();

            mapper.saveRoomWithLoan(rwl);
            Assertions.assertEquals(3L, rwl.getRoomLoanId());
        }
    }
}