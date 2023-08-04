package application.infrastructure;

import application.model.Qna;
import java.util.List;
import java.util.Optional;

public interface QnaRepository {
    void save(Qna qna);

    Optional<Qna> findById(int id);

    List<Qna> findAll();
}
