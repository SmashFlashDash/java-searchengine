package searchengine.services.search;

import lombok.Getter;
import lombok.Setter;

import java.util.Comparator;
import java.util.Objects;

@Setter
@Getter
class Match implements Comparable<Match> {
    private final int start;
    private final int end;
    private final String lemma;
    private final String word;

    public Match(int start, int end, String lemma, String word) {
        this.start = start;
        this.end = end;
        this.lemma = lemma;
        this.word = word;
    }

    @Override
    public int compareTo(Match o) {
        return Comparator.comparing(Match::getStart).compare(this, o);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Match match = (Match) o;
        return start == match.start &&
                end == match.end &&
                lemma.equals(match.lemma);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start);
    }
}
