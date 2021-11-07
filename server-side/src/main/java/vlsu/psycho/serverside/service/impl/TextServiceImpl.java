package vlsu.psycho.serverside.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vlsu.psycho.serverside.model.Text;
import vlsu.psycho.serverside.repository.TextRepository;
import vlsu.psycho.serverside.service.TextService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class TextServiceImpl implements TextService {
    private final TextRepository repository;

    @Override
    @Transactional
    public void save(Text text) {
        repository.save(text);
    }
}
