INSERT INTO users (username, password, enabled) VALUES
                                                    ('adminB', '$2a$12$7Fr0Y.p.FhxUiD9SuYV8VuGbGfhMHdX02HniTpUtfIgz54EsVU1pK', true),
                                                    ('userC', '$2a$12$vxSARELdU4D.O0JLC/Ynd.z.64fyqJaj/rl42wSyQGtBVULR4S7Nu', true),
                                                    ('userD', '$2a$12$BFWb44/Mi9uc4flw.ehx0eFCjDfpBkEQmImJ.RNjU.V4iPE748LiW', true);
INSERT INTO authorities (username, authority) VALUES
                                                  ('adminB', 'ROLE_USER'),
                                                  ('adminB', 'ROLE_ADMIN'),
                                                  ('userC', 'ROLE_USER'),
                                                  ('userD', 'ROLE_USER');

