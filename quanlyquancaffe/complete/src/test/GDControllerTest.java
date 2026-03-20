package com.example.servingwebcontent;

import com.example.servingwebcontent.controller.*;
import com.example.servingwebcontent.database.GiaodichAiven;
import com.example.servingwebcontent.database.myDBConnection;
import com.example.servingwebcontent.model.Giaodich;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GDController.class)
class GDControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private myDBConnection dbConnection;

    // Vì GDController tự tạo new GiaodichAiven(), ta không thể @MockBean trực tiếp
    // => ta cần mock phương thức của đối tượng này thông qua spy/Reflection nếu cần

    @Mock
    private GiaodichAiven giaodichAivenMock;

    @InjectMocks
    private GDController gdController;

    @BeforeEach
    void setup() throws Exception {
        // Mock dữ liệu trả về cho getAllGiaodich()
        List<Giaodich> fakeList = List.of(
                new Giaodich("GD001", "KH001", "2025-08-01", "NV001", 50000, 2)
        );
        when(giaodichAivenMock.getAllGiaodich()).thenReturn(fakeList);
    }

    @Test
    void testHienThiDanhSachGiaodich() throws Exception {
        // Dùng mockMvc gọi GET /giaodich và kiểm tra view + model
        mockMvc.perform(get("/giaodich"))
                .andExpect(status().isOk())
                .andExpect(view().name("giaodich"))
                .andExpect(model().attributeExists("giaodich"));
    }

    @Test
    void testThemGiaodich() throws Exception {
        mockMvc.perform(post("/giaodich/add")
                        .param("maKh", "KH002")
                        .param("timeGd", "2025-08-10")
                        .param("nvGd", "NV002")
                        .param("tongTien", "120000")
                        .param("tongSp", "3"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/giaodich"));
    }

    @Test
    void testSuaGiaodich() throws Exception {
        mockMvc.perform(post("/giaodich/edit")
                        .param("maGd", "GD001")
                        .param("maKh", "KH002")
                        .param("timeGd", "2025-08-10")
                        .param("nvGd", "NV002")
                        .param("tongTien", "150000")
                        .param("tongSp", "4"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/giaodich"));
    }

    @Test
    void testXoaGiaodich() throws Exception {
        mockMvc.perform(post("/giaodich/delete")
                        .param("maGd", "GD001"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/giaodich"));
    }

    @Test
    void testDatHang() throws Exception {
        // Mock DB connection và statement
        Connection mockConn = Mockito.mock(Connection.class);
        PreparedStatement mockStmtGia = Mockito.mock(PreparedStatement.class);
        PreparedStatement mockStmtInsert = Mockito.mock(PreparedStatement.class);
        ResultSet mockRs = Mockito.mock(ResultSet.class);

        when(dbConnection.getConnection()).thenReturn(mockConn);
        when(mockConn.prepareStatement("SELECT Gia FROM sanpham WHERE MaSp = ?")).thenReturn(mockStmtGia);
        when(mockConn.prepareStatement("INSERT INTO donhang(MaGd, MaKh, MaSp, TongSoSp, TongTien) VALUES (?, ?, ?, ?, ?)"))
                .thenReturn(mockStmtInsert);

        when(mockStmtGia.executeQuery()).thenReturn(mockRs);
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getDouble("Gia")).thenReturn(50000.0);

        String jsonGioHang = """
                {
                  "SP001": 2,
                  "SP002": 1
                }
                """;

        mockMvc.perform(post("/dathang")
                        .param("maKH", "KH003")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonGioHang))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Đặt hàng thành công")));
    }
}