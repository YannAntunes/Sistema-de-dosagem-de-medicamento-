package br.com.dosecerta.principal;

import br.com.dosecerta.seguranca.Usuario;
import br.com.dosecerta.seguranca.PerfilUsuario;

import javax.swing.*;

/**
 * Gerenciador de permissões e controle de acesso por perfil de usuário.
 * Responsável por aplicar as regras de visibilidade das abas conforme o perfil.
 */
public class GerenciadorPermissoes {

    /**
     * Aplica as restrições de permissão baseado no perfil do usuário.
     * 
     * @param usuario Usuário logado
     * @param tabs JTabbedPane com as abas da aplicação
     */
    public static void aplicarPermissoes(Usuario usuario, JTabbedPane tabs) {
        if (usuario == null || usuario.getPerfil() == null) {
            return;
        }

        PerfilUsuario perfil = usuario.getPerfil();

        switch (perfil) {
            case ADMIN -> aplicarPermissoesAdmin(tabs);
            case MEDICO -> aplicarPermissoesMedico(tabs);
            case ENFERMEIRO -> aplicarPermissoesEnfermeiro(tabs);
            case RECEPCAO -> aplicarPermissoesRecepcao(tabs);
        }
    }

    /**
     * Admin: Pode acessar todas as abas.
     */
    private static void aplicarPermissoesAdmin(JTabbedPane tabs) {
        // Nenhuma restrição - todas as abas habilitadas
    }

    /**
     * Médico: Não gerencia outros médicos/enfermeiros.
     * Acesso: Pacientes, Consulta, Histórico, Medicamentos
     */
    private static void aplicarPermissoesMedico(JTabbedPane tabs) {
        desabilitarAba(tabs, "Médicos");
        desabilitarAba(tabs, "Enfermeiros");
    }

    /**
     * Enfermeiro: Não gerencia médicos.
     * Acesso: Pacientes, Enfermeiros, Consulta, Histórico, Medicamentos
     */
    private static void aplicarPermissoesEnfermeiro(JTabbedPane tabs) {
        desabilitarAba(tabs, "Médicos");
    }

    /**
     * Recepção: Apenas gerencia pacientes e visualiza histórico.
     * Acesso: Pacientes, Histórico
     */
    private static void aplicarPermissoesRecepcao(JTabbedPane tabs) {
        for (int i = 0; i < tabs.getTabCount(); i++) {
            String titulo = tabs.getTitleAt(i);
            boolean permitido = titulo.equals("Pacientes") || titulo.equals("Histórico");
            tabs.setEnabledAt(i, permitido);
        }
    }

    /**
     * Desabilita uma aba pelo seu título.
     */
    private static void desabilitarAba(JTabbedPane tabs, String nomeAba) {
        for (int i = 0; i < tabs.getTabCount(); i++) {
            if (tabs.getTitleAt(i).equals(nomeAba)) {
                tabs.setEnabledAt(i, false);
                return;
            }
        }
    }

    /**
     * Retorna uma descrição legível do perfil do usuário.
     */
    public static String getDescricaoPerfil(Usuario usuario) {
        if (usuario == null || usuario.getPerfil() == null) {
            return "Desconhecido";
        }
        return usuario.getPerfil().toString();
    }
}
