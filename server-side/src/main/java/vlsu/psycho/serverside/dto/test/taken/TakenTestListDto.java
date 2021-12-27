package vlsu.psycho.serverside.dto.test.taken;

import lombok.Data;

import java.util.List;

@Data
public class TakenTestListDto {
    private List<TakenTestListItemDto> tests;
}
