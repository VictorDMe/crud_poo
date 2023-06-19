INSERT INTO Usuarios(TipoUsuario, login, senha, Nome, Endereco, Telefone, Email) values
(1, 'lucas', '1234', 'Lucas', 'Casa 1', '99999982', 'emaildaora@gmail.com'),
(3, 'victor', '1234', 'Victor', 'Casa 2', '99999982', 'emaildaora@gmail.com'),
(2, 'emauri', '1234', 'Emauri', 'Casa 3', '99999982', 'emaildaora@gmail.com'),
(2, 'pedro', '1234','Pedro', 'Casa 4', '99999982', 'emaildaora@gmail.com'),
(3, 'diego', '1234', 'Diego', 'Casa 5', '99999982', 'emaildaora@gmail.com'),
(1, 'mariana', '1234', 'Mariana', 'Casa 6', '99999982', 'emaildaora@gmail.com');

INSERT INTO Categorias(Nome) values
('ferramentas'),('eletronicos'),
('materiais'),('quadros'), ('softwares');

INSERT INTO Produtos(Nome, Preco, ID_usuario, ID_categoria) values
('Mesa Digitalizadora 1', 1000.00, 3, 2),
('Lapis 1', 10.00, 3, 3),
('Borracha 1', 10.00, 3, 3),
('Caneta Digitalizadora 1', 300.00, 3, 2),
('Quadro 1', 200.00, 4, 4),
('Photoshop', 500.00, 4, 5),
('Ilustrator', 600.00, 4, 5),
('Tablet', 5000.00, 4, 2);

INSERT INTO Vendas(ID_usuario, ID_produto, Data) values	
(1,1,'20/10/2023'),
(1,2,'20/10/2023'),
(6,3,'21/10/2023'),
(6,4,'21/10/2023');
