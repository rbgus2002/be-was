package db;

import model.Question;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class QuestionRepository {

    private static final Map<Integer, Question> questions = new ConcurrentHashMap<>();

    public static Question findById(int id) {
        return questions.get(id);
    }

    public static void addQuestion(Question question) {
        questions.put(question.getId(), question);
    }

    public static Collection<Question> findAll() {
        return questions.values();
    }

    private QuestionRepository() {}
}
