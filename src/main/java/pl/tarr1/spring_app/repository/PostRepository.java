package pl.tarr1.spring_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.tarr1.spring_app.model.Post;
import pl.tarr1.spring_app.model.enums.Category;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    // SELECT * FROM post WHERE category = ? ORDER by submission_date DESC;
    List<Post> findAllByCategoryOrderBySubmissionDateDesc(Category category);

    // Adnotacja Query - pozwala na osadzanie zapytań SQL
    @Query(
            value = "select category, count(*) from post group by category order by submission_date",
            nativeQuery = true)
    List<Object[]> groupPostsIntoCategory();

    // Adnotacja Query - pozwalająca na osadzanie poleceń SQL wprowadzających zmianę
    // :namedParam -> nazwa parametru przekazującego wartość
    // @Param("namedParam") Typ nazwaBiektu
    @Transactional  // adnotacja wspierająca transakcie DB
    @Modifying      // adnotacja tylko do zapytań typu INSERT, UPDATE, DELETE
    @Query(
            value = "update post set title = :title where post_id = :postId",
            nativeQuery = true
    )
    void updatePostTitle(@Param("title") String title, @Param("postId") Long postId );
}
