CREATE DATABASE farmacia;
USE farmacia;

CREATE TABLE remedio (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    fabricante VARCHAR(100),
    principio_ativo VARCHAR(150),
    preco DECIMAL(10,2),
    validade DATE,
    quantidade INT,
    exige_receita BOOLEAN
);

CREATE TABLE cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(15),
    alergias TEXT
);

CREATE TABLE receita (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nomePaciente VARCHAR(100) NOT NULL,
    cpfPaciente VARCHAR(15),
    nomeMedico VARCHAR(100) NOT NULL,
    crmMedico VARCHAR(30),
    idRemedio INT NOT NULL,
    dataEmissao DATE NOT NULL,
    validadeDias INT,
    observacoes TEXT,
    FOREIGN KEY (idRemedio) REFERENCES remedio(id)
);


CREATE TABLE venda (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT,
    data_venda DATETIME,
    valor_total DECIMAL(10,2),
    FOREIGN KEY (cliente_id) REFERENCES cliente(id)
);

CREATE TABLE item_venda (
    id INT AUTO_INCREMENT PRIMARY KEY,
    venda_id INT,
    remedio_id INT,
    quantidade INT,
    subtotal DECIMAL(10,2),
    FOREIGN KEY (venda_id) REFERENCES venda(id),
    FOREIGN KEY (remedio_id) REFERENCES remedio(id)
);

INSERT INTO remedio (nome, fabricante, principio_ativo, preco, validade, quantidade, exige_receita)
VALUES 
('Paracetamol 750mg', 'NeoQuímica', 'Paracetamol', 12.50, '2026-03-10', 150, FALSE),
('Ibuprofeno 400mg', 'Medley', 'Ibuprofeno', 18.90, '2027-01-15', 200, FALSE),
('Amoxicilina 500mg', 'EMS', 'Amoxicilina', 32.00, '2025-11-20', 80, TRUE),
('Dipirona Sódica 1g', 'Sanofi', 'Metamizol', 10.75, '2026-08-05', 300, FALSE),
('Loratadina 10mg', 'Cimed', 'Loratadina', 15.00, '2027-05-01', 120, FALSE),
('Omeprazol 20mg', 'Aché', 'Omeprazol', 22.90, '2026-12-10', 180, FALSE),
('Losartana 50mg', 'Eurofarma', 'Losartana Potássica', 28.50, '2027-10-11', 90, TRUE),
('Metformina 850mg', 'Merck', 'Metformina', 19.80, '2026-07-30', 140, TRUE),
('Azitromicina 500mg', 'EMS', 'Azitromicina', 45.60, '2025-09-22', 60, TRUE),
('Salbutamol Aerosol', 'GSK', 'Salbutamol', 35.00, '2026-01-19', 70, TRUE);

INSERT INTO cliente (nome, cpf, alergias) VALUES
('João Silva', '12345678901', 'Nenhuma'),
('Maria Souza', '12345678902', 'Nenhuma'),
('Pedro Henrique', '12345678903', 'Dipirona'),
('Ana Paula', '12345678904', 'Paracetamol'),
('Lucas Almeida', '12345678905', 'Nenhuma'),
('Carla Silva', '12345678906', 'Ibuprofeno'),
('Rafael Costa', '12345678907', 'AAS'),
('Juliana Martins', '12345678908', 'Nenhuma'),
('Bruno Oliveira', '12345678909', 'Paracetamol'),
('Camila Santos', '12345678910', 'Amoxicilina');

INSERT INTO receita 
(nomePaciente, cpfPaciente, nomeMedico, crmMedico, idRemedio, dataEmissao, validadeDias, observacoes)
VALUES
('João Silva', '12345678901', 'Dr. Marcos Andrade', 'CRM12345', 1, '2025-01-10', 30, 'Uso conforme dor.'),
('Maria Oliveira', '12345678902', 'Dr. Paula Ribeiro', 'CRM99887', 2, '2025-01-12', 10, 'Evitar dirigir.'),
('Pedro Santos', '12345678903', 'Dr. Ricardo Lima', 'CRM44556', 3, '2025-01-15', 60, 'Tomar após as refeições.'),
('Ana Costa', '12345678904', 'Dr. Helena Moura', 'CRM22334', 4, '2025-01-18', 15, 'Atenção a possíveis alergias.'),
('Lucas Pereira', '12345678905', 'Dr. Bruno Matos', 'CRM11223', 5, '2025-01-20', 45, 'Evitar uso prolongado.'),
('Juliana Souza', '12345678906', 'Dr. Carla Farias', 'CRM66778', 6, '2025-01-22', 90, 'Tomar com bastante água.'),
('Bruno Almeida', '12345678907', 'Dr. Fernando Duarte', 'CRM55664', 7, '2025-01-25', 20, 'Suspender em caso de tontura.'),
('Camila Rocha', '12345678908', 'Dr. Adriana Neves', 'CRM33445', 8, '2025-01-28', 30, 'Uso estritamente oral.'),
('Rafael Mendes', '12345678909', 'Dr. Sergio Teles', 'CRM77889', 9, '2025-02-01', 7,  'Aplicar somente à noite.'),
('Carla Martins', '12345678910', 'Dr. Bianca Lopes', 'CRM88990', 10,'2025-02-03', 14, 'Não ultrapassar a dose diária.');



INSERT INTO venda (cliente_id, data_venda, valor_total) VALUES
(1, '2025-02-10 10:30:00', 27.98),
(2, '2025-02-11 14:10:00', 18.00),
(3, '2025-02-12 09:50:00', 42.00),
(4, '2025-02-13 13:22:00', 35.90),
(5, '2025-02-14 11:40:00', 50.00),
(6, '2025-02-15 16:00:00', 30.00),
(7, '2025-02-16 15:10:00', 16.00),
(8, '2025-02-17 17:55:00', 60.00),
(9, '2025-02-18 10:42:00', 14.00),
(10, '2025-02-19 08:20:00', 22.00);


INSERT INTO item_venda (venda_id, remedio_id, quantidade, subtotal) VALUES
(1, 1, 2, 15.98),
(2, 3, 1, 18.00),
(3, 9, 1, 42.00),
(4, 4, 1, 35.90),
(5, 2, 4, 50.00),
(6, 5, 2, 30.00),
(7, 7, 1, 16.00),
(8, 8, 3, 60.00),
(9, 10, 1, 14.00),
(10, 5, 1, 22.00);


