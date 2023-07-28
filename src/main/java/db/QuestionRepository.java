package db;

import model.Question;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class QuestionRepository {

    private static final Map<Integer, Question> questions = new ConcurrentHashMap<>();
    private static int index = 1;

    public static Question findById(int id) {
        return questions.get(id);
    }

    public static synchronized void addQuestion(Question question) {
        questions.put(index++, question);
    }

    public static Collection<Question> findAll() {
        return questions.values();
    }

    public static Set<Map.Entry<Integer, Question>> findAllWithId() {
        return questions.entrySet();
    }

    private QuestionRepository() {}
}
