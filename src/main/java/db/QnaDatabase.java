package db;

import application.model.Qna;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class QnaDatabase {
    private static final Map<Integer, Qna> qnas = Maps.newConcurrentMap();

    public static void addQna(final Qna qna) {
        qnas.put(qna.getId(), qna);
    }

    public static Optional<Qna> findQnaById(int qnaId) {
        return Optional.ofNullable(qnas.get(qnaId));
    }

    public static List<Qna> findAll() {
        return new ArrayList<>(qnas.values());
    }
}
