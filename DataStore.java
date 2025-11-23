package br.com.dosecerta.banco;

import java.util.Optional;
import br.com.dosecerta.cadastro.Enfermeiro;
import br.com.dosecerta.cadastro.Medico;
import br.com.dosecerta.cadastro.Paciente;
import br.com.dosecerta.historico.Consultas;
import br.com.dosecerta.medicamento.Medicamento;
import br.com.dosecerta.calculo.TipoCalculo;

import java.util.ArrayList;
import java.util.List;

public class DataStore {

    private final List<Paciente> pacientes = new ArrayList<>();
    private final List<Medico> medicos = new ArrayList<>();
    private final List<Enfermeiro> enfermeiros = new ArrayList<>();
    private final List<Medicamento> medicamentos = new ArrayList<>();
    private final List<Consultas> consultas = new ArrayList<>();

    private int nextMedId = 1;

    public DataStore() {
        preloadMedicamentos();
    }

    // ===============================================================
    //                  MEDICAMENTOS PADRÃO PARA TESTES
    // ===============================================================
    private void preloadMedicamentos() {

    // === 1-2: pedidos especiais (Tadalafila, Sildenafil) ===
medicamentos.add(new Medicamento(nextMedId++, "Tadalafila (Cialis) 5mg", "Lilly",
        0.07, 20.0, "24h", "Adaptado: dose diária 5mg (convertido para mg/kg)",
        5.0, 1.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Sildenafil (Viagra) 50mg", "Pfizer",
        0.71, 100.0, "24h", "Adaptado: dose típica 50mg (convertido para mg/kg)",
        50.0, 1.0, 0, 0, TipoCalculo.DOSE_MGKG));

// ==============================
// 30 ANTIBIÓTICOS (itens 3..32)
// ==============================
medicamentos.add(new Medicamento(nextMedId++, "Amoxicilina 250mg/5mL", "EMS",
        30.0, 1500.0, "8h", "Suspensão pediátrica",
        50.0, 5.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Ceftriaxona EV 1g", "Roche",
        50.0, 2000.0, "24h", "Antibiótico cefalosporina",
        1000.0, 10.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Azitromicina 200mg/5mL", "Eurofarma",
        10.0, 1000.0, "24h", "Suspensão",
        40.0, 5.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Cefalexina 250mg/5mL", "Prati",
        25.0, 3000.0, "6h", "Suspensão oral",
        50.0, 5.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Ciprofloxacino 500mg", "EMS",
        12.0, 1500.0, "12h", "Fluoroquinolona oral",
        500.0, 20.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Clindamicina 300mg", "Pfizer",
        10.0, 1800.0, "8h", "Lincosamida",
        300.0, 10.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Vancomicina EV 500mg", "Sandoz",
        15.0, 2000.0, "12h", "Infusão lenta",
        500.0, 12.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Gentamicina EV 80mg/2mL", "Teuto",
        7.5, 400.0, "24h", "Aminoglicosídeo",
        80.0, 2.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Metronidazol 500mg EV", "Teuto",
        15.0, 2000.0, "8h", "Antiprotozoário/antibacteriano",
        500.0, 5.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Meropenem EV 500mg", "AstraZeneca",
        20.0, 3000.0, "8h", "Carbapenêmico",
        500.0, 8.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Ampicilina EV 1g", "União Química",
        50.0, 4000.0, "6h", "Penicilina",
        1000.0, 10.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Ertapenem EV 1g", "MD",
        20.0, 2000.0, "24h", "Carbapenêmico de ação prolongada",
        1000.0, 10.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Linezolida 600mg", "Pfizer",
        10.0, 1200.0, "12h", "Oxazolidinona",
        600.0, 10.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Doxiciclina 100mg", "Bayer",
        5.0, 400.0, "12h", "Tetraciclina",
        100.0, 10.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Trimetoprim-Sulfametoxazol 160/800", "Genérico",
        8.0, 1600.0, "12h", "Antibiótico combinado",
        960.0, 20.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Claritromicina 500mg", "Novartis",
        15.0, 1500.0, "12h", "Macrolídeo",
        500.0, 10.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Norfloxacino 400mg", "EMS",
        10.0, 800.0, "12h", "Fluoroquinolona",
        400.0, 10.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Cefuroxima 750mg EV", "Genérico",
        30.0, 3000.0, "12h", "Cefalosporina de 2ª geração",
        750.0, 10.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Cefepima EV 1g", "Genérico",
        40.0, 4000.0, "12h", "Cefalosporina 4ª geração",
        1000.0, 10.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Tigeciclina EV 50mg", "Pfizer",
        8.0, 300.0, "12h", "Glicilciclina",
        50.0, 5.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Fosfomicina 3g", "Genérico",
        40.0, 3000.0, "Única dose", "Uso oral/intravenoso conforme protocolo",
        3000.0, 20.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Rifampicina 300mg", "Genérico",
        10.0, 600.0, "24h", "Antituberculoso",
        300.0, 10.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Isoniazida 300mg", "Genérico",
        5.0, 300.0, "24h", "Antituberculoso",
        300.0, 10.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Polimixina B EV", "Hospitalar",
        2.0, 150.0, "24h", "Antibiótico para multirresistentes",
        100.0, 10.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Colistina EV 1MU", "Hospitalar",
        5.0, 300.0, "8-12h", "Antibacteriano",
        1000000.0, 10.0, 0, 0, TipoCalculo.VOLUME_MLH));

// ==============================
// 30 ANALGÉSICOS / ANTI-INFLAMATÓRIOS (itens 33..62)
// ==============================
medicamentos.add(new Medicamento(nextMedId++, "Dipirona 500mg/mL", "Medley",
        15.0, 2000.0, "6h", "Analgésico/antipirético",
        500.0, 1.0, 15, 25, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Paracetamol 200mg/5mL", "Tylenol",
        15.0, 1000.0, "6h", "Suspensão pediátrica",
        200.0, 5.0, 10, 25, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Ibuprofeno 100mg/5mL", "Alivium",
        10.0, 1200.0, "6-8h", "Suspensão infantil",
        100.0, 5.0, 10, 20, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Naproxeno 250mg", "Genérico",
        8.0, 1000.0, "12h", "AINE oral",
        250.0, 10.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Cetoprofeno 100mg", "Genérico",
        1.5, 300.0, "12h", "AINE",
        100.0, 10.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Nimesulida 100mg", "Aché",
        5.0, 200.0, "12h", "AINE",
        100.0, 10.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Tramadol 50mg", "Medley",
        1.5, 400.0, "6h", "Opioide leve",
        50.0, 5.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Codeína 30mg", "Genérico",
        1.0, 160.0, "6h", "Opióide leve",
        30.0, 5.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Morfina 10mg/mL", "Hosp. Brasil",
        0.2, 50.0, "4h", "Opióide forte – uso hospitalar",
        10.0, 1.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Fentanil 0.05mg/mL", "Hosp. Brasil",
        0.05, 10.0, "1-2h", "Opióide ultra potente",
        0.05, 1.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Dipirona Oral 500mg", "Genérico",
        15.0, 2000.0, "6h", "Comprimido",
        500.0, 10.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Paracetamol 500mg", "Neo Química",
        15.0, 1000.0, "6h", "Comprimido",
        500.0, 10.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Ibuprofeno 400mg", "Advil",
        10.0, 1200.0, "6-8h", "AINE oral",
        400.0, 10.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Ketorolaco 30mg", "Genérico",
        0.8, 90.0, "6h", "AINE parenteral",
        30.0, 1.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Dipirona 1g/2mL EV", "Hypera",
        15.0, 2000.0, "6h", "Analgésico IV",
        1000.0, 2.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Celecoxibe 200mg", "Pfizer",
        0.5, 400.0, "12h", "AINE seletivo COX-2",
        200.0, 10.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Metamizol (Dipirona) 500mg/ml gotas", "Genérico",
        15.0, 2000.0, "6h", "Gotas pediátricas",
        500.0, 1.0, 15, 25, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Oxicodona 10mg", "Hospitalar",
        0.3, 80.0, "8-12h", "Opióide potente",
        10.0, 5.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Nalbufina EV 20mg", "Hospitalar",
        0.2, 40.0, "4-6h", "Opióide agonista-antagonista",
        20.0, 1.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Cloridrato de tramadol 100mg/2mL", "Genérico",
        2.0, 200.0, "6h", "Analgésico injetável",
        100.0, 2.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "AINE tópico (Diclofenaco gel)", "Genérico",
        1.0, 100.0, "uso tópico", "Aplicar topicamente",
        50.0, 50.0, 0, 0, TipoCalculo.DOSE_MGKG));

// ==============================
// 20 EV HOSPITALARES (itens 63..82)
// ==============================
medicamentos.add(new Medicamento(nextMedId++, "Noradrenalina 4mg/250mL", "Hospira",
        0.02, 50.0, "Contínuo", "Vasopressor — bomba de infusão",
        4.0, 250.0, 60, 60, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Norepinefrina (Noradrenalina) 4mg", "Hospira",
        0.02, 50.0, "Contínuo", "Vasopressor",
        4.0, 250.0, 60, 60, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Dopamina EV 200mg/250mL", "Hosp.",
        0.02, 50.0, "Contínuo", "Vasopressor / inotrópico",
        200.0, 250.0, 60, 60, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Dobutamina EV 250mg/50mL", "Hosp.",
        0.02, 50.0, "Contínuo", "Inotrópico",
        250.0, 50.0, 60, 60, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Insulina Regular 100U/mL", "Hospitalar",
        0.5, 20.0, "Variável", "Hormônio – usar cálculo específico",
        100.0, 1.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Insulina NPH 100U/mL", "Hospitalar",
        0.5, 20.0, "12h", "Basal",
        100.0, 1.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Heparina EV 5.000 UI/mL", "Hospitalar",
        100.0, 2000.0, "Contínuo", "Anticoagulação",
        5000.0, 1.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Enoxaparina 40mg/0.4mL", "Sanofi",
        1.0, 100.0, "24h", "Anticoagulante SC",
        40.0, 0.4, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Noradrenalina Infusão 8mg/250mL", "Hospira",
        0.02, 50.0, "Contínuo", "Vasopressor — diluído",
        8.0, 250.0, 60, 60, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Bicarbonato EV 8.4% 10mL", "Hospitalar",
        1.0, 100.0, "Urgente", "Antídoto/ajuste de pH",
        840.0, 10.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Cloreto de Sódio 0.9% 1000mL", "Hospitalar",
        20.0, 500.0, "Infusão", "Solução salina",
        9.0, 1000.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Ringer Lactato 1000mL", "Hospitalar",
        20.0, 500.0, "Infusão", "Cristaloide",
        9.0, 1000.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Albumina 20% 100mL", "Hospitalar",
        0.5, 50.0, "Contínuo", "Colóide",
        200.0, 100.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Fenilefrina EV 10mg/10mL", "Hospira",
        0.02, 10.0, "Contínuo", "Vasopressor",
        10.0, 10.0, 60, 60, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Amiodarona EV 50mg/mL", "Cristália",
        5.0, 600.0, "24h", "Antiarrítmico",
        50.0, 20.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Dobutamina 250mg/20mL", "Hosp.",
        0.02, 50.0, "Contínuo", "Inotrópico",
        250.0, 20.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Norepinefrina 8mg/250mL", "Hospira",
        0.02, 50.0, "Contínuo", "Vasopressor",
        8.0, 250.0, 60, 60, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Metilprednisolona EV 125mg", "Solu-Medrol",
        0.2, 500.0, "24h", "Corticoide de ação rápida",
        125.0, 5.0, 0, 0, TipoCalculo.VOLUME_MLH));

// ==============================
// 18 GOTES / GOTAS (itens 83..100)
// ==============================
medicamentos.add(new Medicamento(nextMedId++, "Paracetamol gotas 100mg/mL", "Tylenol",
        15.0, 1000.0, "6h", "Gotas pediátricas",
        100.0, 1.0, 10, 25, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Dipirona gotas 500mg/mL", "Genérico",
        15.0, 2000.0, "6h", "Gotas pediátricas",
        500.0, 1.0, 12, 30, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Ibuprofeno gotas 100mg/mL", "Alivium",
        10.0, 800.0, "6-8h", "Gotas pediátricas",
        100.0, 1.0, 10, 20, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Lorazepam gotas 2mg/mL", "Genérico",
        0.02, 6.0, "12h", "Ansiolítico — adaptar para gotas",
        2.0, 1.0, 10, 20, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Naproxeno gotas 25mg/mL", "Genérico",
        8.0, 500.0, "12h", "Gotas anti-inflamatórias pediátricas",
        25.0, 1.0, 8, 18, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Cetirizina gotas 10mg/mL", "Genérico",
        0.1, 20.0, "24h", "Antialérgico gota",
        10.0, 1.0, 10, 20, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Gotas para tosse xarope 50mg/mL", "Genérico",
        5.0, 500.0, "8h", "Xarope/gotas",
        50.0, 5.0, 10, 25, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Cloranfenicol gotas oftálmicas 1%", "Genérico",
        0.1, 10.0, "Uso tópico", "Antibiótico oftálmico — adaptar",
        10.0, 1.0, 0, 0, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Nistatina gotas 50mg/mL", "Neo Química",
        5.0, 500.0, "6h", "Antifúngico oral para bebês",
        50.0, 1.0, 8, 12, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Fenilefrina gotas nasais 0.5%", "Genérico",
        0.1, 10.0, "Uso conforme necessidade", "Vasoconstritor nasal — adaptar",
        5.0, 1.0, 0, 0, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Salbutamol gotas 2mg/mL", "Genérico",
        0.2, 20.0, "6-8h", "Brônquico — adaptar",
        2.0, 1.0, 8, 18, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Complexo vitamínico gotas", "Genérico",
        0.1, 100.0, "Diário", "Suplemento em gotas",
        10.0, 5.0, 10, 20, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Gotas para dor de ouvido (Analgésico local)", "Genérico",
        0.1, 10.0, "Uso tópico", "Uso otológico — adaptar",
        5.0, 1.0, 0, 0, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Xarope expectorante gotas 10mg/mL", "Genérico",
        5.0, 200.0, "8h", "Expectorante",
        10.0, 5.0, 8, 20, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Gotas multivitamínicas pediátricas", "Marca X",
        0.1, 50.0, "Diário", "Suplemento",
        10.0, 5.0, 5, 15, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Gotas anestésicas locais", "Genérico",
        0.1, 10.0, "Uso tópico", "Anestésico local — adaptar",
        10.0, 1.0, 0, 0, TipoCalculo.GOTAS_MIN));

        medicamentos.add(new Medicamento(nextMedId++, "Meloxicam 15mg", "Eurofarma",
        0.25, 15.0, "24h", "AINE sistêmico",
        15.0, 10.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Etodolaco 400mg", "Genérico",
        5.0, 800.0, "12h", "Anti-inflamatório",
        400.0, 10.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Indometacina 50mg", "Genérico",
        1.0, 150.0, "8h", "AINE potente",
        50.0, 10.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Benzetacil 1.200.000 UI", "Eurofarma",
        50.0, 2400000.0, "Dose única", "Benzilpenicilina benzatina",
        1200000.0, 4.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Cefazolina 1g EV", "Genérico",
        25.0, 3000.0, "8h", "Profilaxia e tratamento",
        1000.0, 10.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Claritromicina Suspensão 250mg/5mL", "Genérico",
        15.0, 1000.0, "12h", "Macrolídeo em suspensão",
        50.0, 5.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Manitol 20% 250mL", "Hospitalar",
        1.0, 100.0, "Conforme protocolo", "Diurético osmótico EV",
        200.0, 250.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Furosemida EV 20mg/2mL", "Genérico",
        1.0, 80.0, "8-12h", "Diurético EV",
        10.0, 2.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Metoclopramida EV 10mg/2mL", "Genérico",
        0.2, 20.0, "8h", "Anti-emético EV",
        5.0, 2.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Simeticona 75mg/mL gotas", "Luftal",
        3.0, 180.0, "6h", "Antigases infantil",
        75.0, 1.0, 10, 20, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Zyrtec (Cetirizina) 10mg/mL", "Janssen",
        0.1, 20.0, "24h", "Antialérgico infantil",
        10.0, 1.0, 10, 20, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Dimeticona 100mg/mL gotas", "Genérico",
        2.0, 200.0, "6h", "Antiflatulento",
        100.0, 1.0, 10, 20, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Prednisolona 3mg/mL", "EMS",
        1.0, 60.0, "12-24h", "Corticoide infantil",
        3.0, 5.0, 10, 25, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Dexametasona EV 4mg/mL", "Neo Química",
        0.15, 20.0, "24h", "Corticoide de alta potência",
        4.0, 2.0, 0, 0, TipoCalculo.VOLUME_MLH));

medicamentos.add(new Medicamento(nextMedId++, "Loratadina 1mg/mL gotas", "Genérico",
        0.1, 10.0, "24h", "Antialérgico",
        1.0, 5.0, 10, 20, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Fexofenadina 30mg/mL", "Sanofi",
        0.5, 180.0, "12-24h", "Anti-histamínico",
        30.0, 5.0, 10, 25, TipoCalculo.GOTAS_MIN));

medicamentos.add(new Medicamento(nextMedId++, "Ciclobenzaprina 10mg", "Mantecorp",
        0.2, 30.0, "8-12h", "Relaxante muscular",
        10.0, 10.0, 0, 0, TipoCalculo.DOSE_MGKG));

medicamentos.add(new Medicamento(nextMedId++, "Ceftazidima EV 1g", "Genérico",
        30.0, 4000.0, "8h", "Cefalosporina 3ª geração",
        1000.0, 10.0, 0, 0, TipoCalculo.VOLUME_MLH));
    }

    // ==========================================================
    //                     CRUD BÁSICO
    // ==========================================================

    public void addPaciente(Paciente p) { pacientes.add(p); }
    public void addMedico(Medico m) { medicos.add(m); }
    public void addEnfermeiro(Enfermeiro e) { enfermeiros.add(e); }
    public void addConsultas(Consultas c) { consultas.add(c); }

    public List<Paciente> getPacientes() { return pacientes; }
    public List<Medico> getMedicos() { return medicos; }
    public List<Enfermeiro> getEnfermeiros() { return enfermeiros; }
    public List<Medicamento> getMedicamentos() { return medicamentos; }
    public List<Consultas> getConsultas() { return consultas; }

    // ==========================================================
    //                    MÉTODOS AUXILIARES
    // ==========================================================

    public Optional<Medicamento> findMedicamentoById(int id) {
        return medicamentos.stream().filter(m -> m.getId() == id).findFirst();
    }

    public boolean cpfJaExiste(String cpf) {
        return pacientes.stream().anyMatch(p -> p.getCpf().equalsIgnoreCase(cpf));
    }
}