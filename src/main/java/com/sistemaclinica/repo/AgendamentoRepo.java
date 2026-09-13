package com.sistemaclinica.repo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.sistemaclinica.entity.Agendamento;

@Repository
//@RequestMapping("/api/agendamentos")
public interface AgendamentoRepo extends JpaRepository<Agendamento, Integer> {
	
	// ✅ Método 1: Buscar por intervalo (funciona!)
    List<Agendamento> findByDataAgendamentoBetween(LocalDateTime inicio, LocalDateTime fim);
    
    
    
    @Query(value = """
        WITH RECURSIVE todos_horarios AS (
        SELECT TIME('07:00:00') as hora
        UNION ALL
        SELECT ADDTIME(hora, '00:30:00')
        FROM todos_horarios
        WHERE hora < TIME('19:00:00')
        )
        SELECT 
            TIME_FORMAT(th.hora, '%H:%i') as label,
            REPLACE(TIME_FORMAT(th.hora, '%H:%i'), ':', '-') as `value`,
            CASE 
                WHEN a.id IS NOT NULL THEN 1
                ELSE 0
            END as ocupado,
            a.observacoes,
            p.nomecompleto as paciente_nome,
            p.cpf as cpf,
            p.telefone as telefone,
            a.status,
            u.nome as operadoratendente,          -- ← Nome do operador
            a.id as agendamento_id
        FROM todos_horarios th
        LEFT JOIN agendamentos a 
            ON a.medico = :medicoId
            AND DATE(a.dataagendamento) = :data
            AND TIME(a.dataagendamento) = th.hora
            AND a.status NOT IN ('cancelado', 'remarcado')
        LEFT JOIN pacientes p 
            ON a.paciente = p.id
        LEFT JOIN usersatendentes u              -- ← JOIN com a tabela de operadores
            ON a.operadoratendente = u.id
        ORDER BY th.hora
        """, nativeQuery = true)
        List<HorarioDisponivelProjection> findHorariosDisponiveisPorMedicoEData(
            @Param("medicoId") Long medicoId,
            @Param("data") LocalDate data
        );
    
	    interface HorarioDisponivelProjection {
	        String getLabel();      // Ex: "07:00"
	        String getValue();      // Ex: "07-00"
	        String getObservacoes();
            String getPacienteNome();
            String getStatus();
            String getOperadorAtendente();
            String getCpf();
            String getTelefone();
            Long getAgendamentoId();
	        Integer getOcupado();   // true = ocupado, false = disponível
	    }
    
    //Método 2: Buscar agendamentos de hoje CORRIGIDO
    @Query("SELECT a FROM Agendamento a WHERE " +
           "a.dataAgendamento >= :inicioHoje AND a.dataAgendamento < :inicioAmanha " +
           "ORDER BY a.dataAgendamento")
    List<Agendamento> findAgendamentosDeHoje(
        @Param("inicioHoje") LocalDateTime inicioHoje,
        @Param("inicioAmanha") LocalDateTime inicioAmanha
    );
    
    //Método 2b: Helper para hoje (default method)
    default List<Agendamento> findAgendamentosDeHoje() {
        LocalDate hoje = LocalDate.now();
        LocalDateTime inicioHoje = hoje.atStartOfDay();
        LocalDateTime inicioAmanha = hoje.plusDays(1).atStartOfDay();
        return findAgendamentosDeHoje(inicioHoje, inicioAmanha);
    }
    
    //Método 3: Buscar por data específica CORRIGIDO
    @Query("SELECT a FROM Agendamento a WHERE " +
           "a.dataAgendamento >= :inicioDia AND a.dataAgendamento < :inicioProximoDia AND a.status <> 'reserva' " +
           "ORDER BY a.dataAgendamento")
    List<Agendamento> findByData(
        @Param("inicioDia") LocalDateTime inicioDia,
        @Param("inicioProximoDia") LocalDateTime inicioProximoDia
    );
    
    // Método 3b: Helper para data específica
    default List<Agendamento> findByData(LocalDate data) {
        LocalDateTime inicioDia = data.atStartOfDay();
        LocalDateTime inicioProximoDia = data.plusDays(1).atStartOfDay();
        return findByData(inicioDia, inicioProximoDia);
    }
    
    
    // Método 4: Buscar por médico e intervalo
    @Query("SELECT a FROM Agendamento a WHERE a.medicoBean.id = :medicoId " +
            "AND a.dataAgendamento >= :inicio " +
            "AND a.dataAgendamento < :fim " +
            "ORDER BY a.dataAgendamento")
    List<Agendamento> findByMedicoIdAndDataBetween(
         @Param("medicoId") Long medicoId,
         @Param("inicio") LocalDateTime inicio,
         @Param("fim") LocalDateTime fim
    );
    
    //Método 4b: Helper para médico hoje
    default List<Agendamento> findAgendamentosDeHojePorMedico(Long medicoId) {
        LocalDate hoje = LocalDate.now();
        LocalDateTime inicioHoje = hoje.atStartOfDay();
        LocalDateTime fimHoje = hoje.plusDays(1).atStartOfDay();
        return findByMedicoIdAndDataBetween(medicoId, inicioHoje, fimHoje);
    }
    
    //Método 5: Buscar por status e intervalo CORRIGIDO
    @Query("SELECT a FROM Agendamento a WHERE a.status = :status " +
           "AND a.dataAgendamento >= :inicioHoje AND a.dataAgendamento < :inicioAmanha " +
           "ORDER BY a.dataAgendamento")
    List<Agendamento> findAgendamentosDeHojePorStatus(
        @Param("status") String status,
        @Param("inicioHoje") LocalDateTime inicioHoje,
        @Param("inicioAmanha") LocalDateTime inicioAmanha
    );
    
    //Método 5b: Helper para status hoje
    default List<Agendamento> findAgendamentosDeHojePorStatus(String status) {
        LocalDate hoje = LocalDate.now();
        LocalDateTime inicioHoje = hoje.atStartOfDay();
        LocalDateTime inicioAmanha = hoje.plusDays(1).atStartOfDay();
        return findAgendamentosDeHojePorStatus(status, inicioHoje, inicioAmanha);
    }
    
    //Método 6: Usando FUNCTION para MySQL (alternativa)
    @Query("SELECT a FROM Agendamento a WHERE " +
           "FUNCTION('DATE', a.dataAgendamento) = CURRENT_DATE " +
           "ORDER BY a.dataAgendamento ASC")
    List<Agendamento> findAgendamentosDeHojeOrdenadosComFunction();
    
    //Método 7: Native query (100% funcional)
    @Query(value = "SELECT * FROM agendamentos WHERE " +
                   "DATE(dataagendamento) = CURDATE() " +
                   "ORDER BY dataagendamento ASC", 
           nativeQuery = true)
    List<Agendamento> findAgendamentosDeHojeNative();

}
