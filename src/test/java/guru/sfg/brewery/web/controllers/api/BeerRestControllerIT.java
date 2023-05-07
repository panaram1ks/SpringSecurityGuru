package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.web.controllers.BaseIT;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest // do not see some beans in context
@SpringBootTest // use all spring context
public class BeerRestControllerIT extends BaseIT {

    @Test
    void deleteBeerAuthFromParametersBadCreds() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/1111-1111111111-1111111111-1111")
                        .param("Api-Key", "spring")
                        .param("Api-Secret", "guruXXXX")
                )
                .andExpect(status().isUnauthorized());

    }
    @Test
    void deleteBeerAuthFromParameters() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/1111-1111111111-1111111111-1111")
                        .param("Api-Key", "spring")
                        .param("Api-Secret", "guru")
                )
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeerBadCreds() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/1111-1111111111-1111111111-1111")
                        .header("Api-Key", "spring")
                        .header("Api-Secret", "guruXXXX")
                )
                .andExpect(status().isUnauthorized());

    }

    @Test
    void deleteBeer() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/1111-1111111111-1111111111-1111")
                        .header("Api-Key", "spring")
                        .header("Api-Secret", "guru")
                )
                .andExpect(status().isOk());

    }

    @Test
    void findBeers() throws Exception {
        mockMvc.perform(get("/api/v1/beer"))
                .andExpect(status().isOk());
    }

//    @Test
//    void findBeerById() throws Exception {
//        mockMvc.perform(get("/api/v1/beer/1111-1111111111-1111111111-1111"))
//                .andExpect(status().isOk());
//    }

    @Test
    void findBeerByUpc() throws Exception {
        mockMvc.perform(get("/api/v1/beerUpc/@6311234200036"))
                .andExpect(status().isOk());
    }


}
