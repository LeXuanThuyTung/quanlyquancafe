package com.example.servingwebcontent;

import com.example.servingwebcontent.controller.*;
import com.example.servingwebcontent.database.myDBConnection;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(KhachHangController.class)
class KhachHangControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private myDBConnection dbConnection;

    @Test
    void testGetAllCustomers() throws Exception {
        Connection mockConn = Mockito.mock(Connection.class);
        PreparedStatement mockStmt = Mockito.mock(PreparedStatement.class);
        ResultSet mockRs = Mockito.mock(ResultSet.class);

        Mockito.when(dbConnection.getConnection()).thenReturn(mockConn);
        Mockito.when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        Mockito.when(mockStmt.executeQuery()).thenReturn(mockRs);

        Mockito.when(mockRs.next()).thenReturn(true, false);
        Mockito.when(mockRs.getString("code")).thenReturn("C001");
        Mockito.when(mockRs.getString("name")).thenReturn("Nguyen Van A");
        Mockito.when(mockRs.getString("phone")).thenReturn("0123456789");
        Mockito.when(mockRs.getString("address")).thenReturn("Hanoi");

        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code").value("C001"))
                .andExpect(jsonPath("$[0].name").value("Nguyen Van A"));
    }

    @Test
    void testAddCustomer() throws Exception {
        Connection mockConn = Mockito.mock(Connection.class);
        PreparedStatement mockStmt = Mockito.mock(PreparedStatement.class);

        Mockito.when(dbConnection.getConnection()).thenReturn(mockConn);
        Mockito.when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);

        String json = """
                {
                  "code": "C002",
                  "name": "Tran Thi B",
                  "phone": "0987654321",
                  "address": "HCM"
                }
                """;

        mockMvc.perform(post("/api/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Thêm khách hàng thành công"));
    }

    @Test
    void testUpdateCustomer() throws Exception {
        Connection mockConn = Mockito.mock(Connection.class);
        PreparedStatement mockStmt = Mockito.mock(PreparedStatement.class);

        Mockito.when(dbConnection.getConnection()).thenReturn(mockConn);
        Mockito.when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        Mockito.when(mockStmt.executeUpdate()).thenReturn(1);

        String json = """
                {
                  "code": "C002",
                  "name": "Tran Thi B Updated",
                  "phone": "0987654321",
                  "address": "HCM"
                }
                """;

        mockMvc.perform(put("/api/customers/C002")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Cập nhật thành công"));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        Connection mockConn = Mockito.mock(Connection.class);
        PreparedStatement mockStmt = Mockito.mock(PreparedStatement.class);

        Mockito.when(dbConnection.getConnection()).thenReturn(mockConn);
        Mockito.when(mockConn.prepareStatement(anyString())).thenReturn(mockStmt);
        Mockito.when(mockStmt.executeUpdate()).thenReturn(1);

        mockMvc.perform(delete("/api/customers/C002"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Xóa thành công"));
    }
}