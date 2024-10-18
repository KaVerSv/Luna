insert into public.books (id, title, author, publish_date, description, likes, dislikes, price, image)
values  (4, 'Metro 2033', 'Dmitry Glukhovsky', 2002, 'Metro 2033 is a 2002 post-apocalyptic fiction novel by Russian author Dmitry Glukhovsky. It is set within the Moscow Metro, where the last survivors hide after a global nuclear holocaust. It has been followed by two sequels, Metro 2034 and Metro 2035, and spawned the Metro media franchise.', 15000, 1402, 49.99, '/img/book_images/Metro2033.jpg'),
        (1, 'At the Mountains of Madness', 'Lovecraft H.P.', 1931, 'The story details the events of a disastrous expedition to Antarctica in September 1930, and what is found there by a group of explorers led by the narrator, Dr. William Dyer of Miskatonic University. Throughout the story, Dyer details a series of previously untold events in the hope of deterring another group of explorers who wish to return to the continent. These events include the discovery of an ancient civilization older than the human race, and realization of Earth''s past told through various sculptures and murals.', 18756, 1200, 20.59, '/img/book_images/Mountains_of_Madness.jpg'),
        (3, 'Dune', 'Frank Herbert', 1965, 'Dune is a 1965 epic science fiction novel book by American author Frank Herbert, originally published as two separate serials in Analog magazine. It tied with Roger Zelazny''s This Immortal for the Hugo Award for Best Novel and won the inaugural Nebula Award for Best Novel in 1966.', 90450, 9000, 45.99, '/img/book_images/dune.jpg'),
        (7, 'Blood of Elves', 'Andrzej Sapkowski', 1994, 'Blood of Elves is the first novel in The Witcher series written by the Polish fantasy writer Andrzej Sapkowski, first published in Poland in 1994. It is a sequel to the Witcher short stories collected in the books The Last Wish and Sword of Destiny and is followed by Time of Contempt.', 45000, 8000, 59.99, '/img/book_images/blood-of-elves.jpg'),
        (2, '1984', 'George Orwell', 1949, 'The story takes place in an imagined future in an unspecified year believed to be 1984, when much of the world is in perpetual war. Great Britain, now known as Airstrip One, has become a province of the totalitarian superstate Oceania, which is led by Big Brother, a dictatorial leader supported by an intense cult of personality manufactured by the Party''s Thought Police. The Party engages in omnipresent government surveillance and, through the Ministry of Truth, historical negationism and constant propaganda to persecute individuality and independent thinking.', 24500, 4200, 45.99, '/img/book_images/1984.jpg'),
        (6, 'A Song of Ice and Fire', 'George R. R. Martin', 1996, 'A Game of Thrones is the first novel in A Song of Ice and Fire, a series of fantasy novels by American author George R. R. Martin. It was first published on August 1, 1996. The novel won the 1997 Locus Award and was nominated for both the 1997 Nebula Award and the 1997 World Fantasy Award.', 80100, 10200, 59.99, '/img/book_images/a-song-of-ice-and-fire.jpg'),
        (5, 'Hyperion', 'Dan Simmons', 1989, 'Hyperion is a 1989 science fiction novel by American author Dan Simmons. The first book of his Hyperion Cantos series, it won the Hugo Award for best novel. The plot of the novel features multiple time-lines and characters. It follows a similar structure to The Canterbury Tales by Geoffrey Chaucer.', 12000, 2002, 69.99, '/img/book_images/hyperion.jpg');

insert into recommended (id, name)
values  (1, 'featured');

insert into recommended_books (recommended_id, book_id)
values  ( 1, 1),
        ( 1, 2),
        ( 1, 3),
        ( 1, 4),
        ( 1, 5),
        ( 1, 6),
        ( 1, 7);

insert into roles (id, name)
values  ( 1, 'ROLE_USER'),
        ( 2, 'ROLE_ADMIN'),
        ( 3, 'ROLE_PUBLISHER');
