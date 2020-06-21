package com.aio.knimer.model;

import com.vladmihalcea.hibernate.type.json.JsonBlobType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@TypeDef(name = "jsonb", typeClass = JsonBlobType.class)
public class Report {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private ChartType chartType;
  private String reportTypeCode;
  private LocalDate reportDate;
  @Type(type = "jsonb")
  private String jsonData;

}
