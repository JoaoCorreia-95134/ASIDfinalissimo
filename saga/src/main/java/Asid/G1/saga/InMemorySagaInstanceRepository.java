package Asid.G1.saga;

import io.eventuate.tram.sagas.orchestration.SagaInstance;
import io.eventuate.tram.sagas.orchestration.SagaInstanceRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemorySagaInstanceRepository implements SagaInstanceRepository {

    private final Map<String, SagaInstance> store = new HashMap<>();

    @Override
    public void save(SagaInstance sagaInstance) {
        store.put(sagaInstance.getId(), sagaInstance);
    }

    @Override
    public SagaInstance find(String sagaType, String sagaId) {
        return store.get(sagaId);
    }

    @Override
    public void update(SagaInstance sagaInstance) {
        store.put(sagaInstance.getId(), sagaInstance);
    }
}
