INSERT INTO public.adjective (id, effect, name) VALUES (0, '+', 'meilleur');
INSERT INTO public.adjective (id, effect, name) VALUES (1, '-', 'mauvais');
INSERT INTO public.adjective (id, effect, name) VALUES (2, '+', 'plus');
INSERT INTO public.adjective (id, effect, name) VALUES (3, '-', 'moins');

INSERT INTO public.category (id, name) VALUES (0, 'fruit');
INSERT INTO public.category (id, name) VALUES (1, 'légume');
INSERT INTO public.category (id, name) VALUES (2, 'céréale');

INSERT INTO public.comparator (id, name, value, parameter_number, parameter_type) VALUES (0, 'supérieur', '>', 1, 'numeric');
INSERT INTO public.comparator (id, name, value, parameter_number, parameter_type) VALUES (1, 'entre', 'BETWEEN', 2, 'numeric');
INSERT INTO public.comparator (id, name, value, parameter_number, parameter_type) VALUES (2, 'égal', '=', 1, 'numeric');
INSERT INTO public.comparator (id, name, value, parameter_number, parameter_type) VALUES (3, 'inférieur', '<', 1, 'numeric');

INSERT INTO public.criteria (id, best_value, label, name, type_criteria) VALUES (0, '-', 'price', 'prix', 'numeric');
INSERT INTO public.criteria (id, best_value, label, name, type_criteria) VALUES (1, '-', 'price', 'cher', 'numeric');
INSERT INTO public.criteria (id, best_value, label, name, type_criteria) VALUES (3, '+', 'quality', 'qualité', 'numeric');

INSERT INTO public.product (id, name, price, quality, category_id) VALUES (1, 'pêche', 3000, 7, 0);
INSERT INTO public.product (id, name, price, quality, category_id) VALUES (2, 'poire', 2500, 8, 0);
INSERT INTO public.product (id, name, price, quality, category_id) VALUES (3, 'pomme', 3000, 9, 0);
INSERT INTO public.product (id, name, price, quality, category_id) VALUES (4, 'ananas', 4000, 3, 0);
INSERT INTO public.product (id, name, price, quality, category_id) VALUES (5, 'carotte', 3000, 5, 1);
INSERT INTO public.product (id, name, price, quality, category_id) VALUES (6, 'tomate', 2000, 4, 1);
INSERT INTO public.product (id, name, price, quality, category_id) VALUES (7, 'onion', 5000, 7, 1);
INSERT INTO public.product (id, name, price, quality, category_id) VALUES (8, 'riz', 3000, 8, 2);
INSERT INTO public.product (id, name, price, quality, category_id) VALUES (9, 'manioc', 2000, 6, 2);
INSERT INTO public.product (id, name, price, quality, category_id) VALUES (0, 'banane', 2000, 5, 0);
