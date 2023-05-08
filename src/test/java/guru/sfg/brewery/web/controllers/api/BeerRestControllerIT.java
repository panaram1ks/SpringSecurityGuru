package guru.sfg.brewery.web.controllers.api;

import guru.sfg.brewery.web.controllers.BaseIT;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest // do not see some beans in context
@SpringBootTest // use all spring context
public class BeerRestControllerIT extends BaseIT {

//    @Test
//    void deleteBeerHttpBasic() throws Exception {
//        mockMvc.perform(delete("/api/v1/beer/f4c6e3ee-c8d1-4bff-8b88-5d5a2d01b4a8")
//                        .with(httpBasic("spring", "guru")))
//                .andExpect(status().is2xxSuccessful());
//    }

    @Test
    void deleteBeerHttpBasicUserRole() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/f4c6e3ee-c8d1-4bff-8b88-5d5a2d01b4a8")
                        .with(httpBasic("user", "password")))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteBeerHttpBasicCustomerRole() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/f4c6e3ee-c8d1-4bff-8b88-5d5a2d01b4a8")
                        .with(httpBasic("scott", "tiger")))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteBeerAuthFromParametersBadCreds() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/f4c6e3ee-c8d1-4bff-8b88-5d5a2d01b4a8")
                        .param("Api-Key", "spring")
                        .param("Api-Secret", "guruXXXX")
                )
                .andExpect(status().isUnauthorized());

    }

    @Test
    void deleteBeerAuthFromParameters() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/f4c6e3ee-c8d1-4bff-8b88-5d5a2d01b4a8")
                        .param("Api-Key", "spring")
                        .param("Api-Secret", "guru")
                )
                .andExpect(status().isOk());
    }

    @Test
    void deleteBeerBadCreds() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/f4c6e3ee-c8d1-4bff-8b88-5d5a2d01b4a8")
                        .header("Api-Key", "spring")
                        .header("Api-Secret", "guruXXXX")
                )
                .andExpect(status().isUnauthorized());

    }

    @Test
    void deleteBeer() throws Exception {
        mockMvc.perform(delete("/api/v1/beer/f4c6e3ee-c8d1-4bff-8b88-5d5a2d01b4a8")
                        .header("Api-Key", "spring")
                        .header("Api-Secret", "guru")
                )
                .andExpect(status().isOk());

    }

//    @Test
//    void findBeers() throws Exception {
//        mockMvc.perform(get("/api/v1/beer"))
//                .andExpect(status().isOk());
//    }

//    @Test
//    void findBeerById() throws Exception {
//        mockMvc.perform(get("/api/v1/beer/f4c6e3ee-c8d1-4bff-8b88-5d5a2d01b4a8"))
//                .andExpect(status().isOk());
//    }

//    @Test
//    void findBeerByUpc() throws Exception {
//        mockMvc.perform(get("/api/v1/beerUpc/@6311234200036"))
//                .andExpect(status().isOk());
//    }


}
