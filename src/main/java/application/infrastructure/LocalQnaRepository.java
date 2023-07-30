package application.infrastructure;

import application.model.Qna;
import db.QnaDatabase;
import java.util.List;
import java.util.Optional;

public class LocalQnaRepository implements QnaRepository {
    @Override
    public void save(final Qna qna) {
        QnaDatabase.addQna(qna);
    }

    @Override
    public Optional<Qna> findById(int id) {
        return QnaDatabase.findQnaById(id);
    }

    @Override
    public List<Qna> findAll() {
        return QnaDatabase.findAll();
    }
}
