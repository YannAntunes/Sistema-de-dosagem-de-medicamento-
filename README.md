📘 DoseCerta — Sistema de Prescrição e Cálculo de Medicamentos

🩺 Sobre o Projeto

O DoseCerta é um sistema profissional desenvolvido para auxiliar médicos e enfermeiros no cálculo de medicamentos, geração de receitas e registro de consultas.
Criado em Java (Swing) e utilizando armazenamento em memória (DataStore), ele simula um sistema real de prescrição, servindo como base para futura integração com banco de dados.

⚙️ Funcionalidades

👥 Gerenciamento de Pacientes

Cadastro com nome, CPF validado, idade e peso

Bloqueio de CPF duplicado

Exibição em tabela


🧑‍⚕️ Gerenciamento de Profissionais

Médicos: Nome, CRM, UF

Enfermeiros: Nome, COREN, UF

Listagem e validações completas


💊 Medicamentos Pré-carregados

O DataStore inclui 10 medicamentos profissionais, cada um com:

Nome e marca

Dose por Kg

Dose máxima

Intervalo

Notas

Volume, concentração, fator de gotejamento e tempo de infusão

Tipo de cálculo padrão (PESO, VOLUME ou GOTEJAMENTO)

🧮 Cálculo Farmacológico Automático

O sistema suporta as 3 principais fórmulas profissionais:

1️⃣ Cálculo por Peso (mg/kg)
dose = peso × dosePorKg

2️⃣ Cálculo por Volume (mL)
volumeAplicar = (dosePrescrita × volumeFrasco) / mgTotal

3️⃣ Cálculo por Gotejamento (gotas/min)
gotejamento = (volume × fatorGotas) / tempo


O cálculo é aplicado automaticamente conforme o tipo de cálculo do medicamento.

📝 Consultas e Receitas

Seleção de profissional, paciente e medicamento

Ficha completa do remédio

Dose calculada automaticamente

Geração de receita formatada

Registro em histórico de consultas

📄 Exportação de Receita

A receita pode ser exportada como arquivo .txt.

📚 Histórico Completo

Consulta registrada com:

Data

Paciente

Profissional

Medicamento

Dose aplicada

🏗️ Arquitetura
src/

 ├─ principal/         → Interface Swing (InterFace.java)
 
 ├─ banco/             → DataStore

 ├─ cadastro/          → Paciente, Médico, Enfermeiro

 ├─ medicamento/       → Medicamento
 
 ├─ calculo/           → TipoCalculo (PESO, VOLUME, GOTEJAMENTO)
 
 ├─ historico/         → Consultas
 
 ├─ util/              → ValidadorCPF, GeradorReceita

🚀 Requisitos

Java 17+

🔮 Roadmap (Futuro)

Integração com MySQL/MariaDB

CRUD completo de medicamentos

Login e níveis de acesso

Relatórios PDF

Dashboard com métricas

API REST (Spring Boot)

Aplicativo mobile (Flutter)

🧑‍💻 Autor

Yann Antunes

Desenvolvedor e estudante de TI

Projeto acadêmico — 2025
