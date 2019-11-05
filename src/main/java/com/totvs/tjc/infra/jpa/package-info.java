@TypeDef(name = "ContaId", typeClass = IdCustomTypes.Conta.class, defaultForType = ContaId.class)
@TypeDef(name = "EmprestimoId", typeClass = IdCustomTypes.Emprestimo.class, defaultForType = EmprestimoId.class)
@TypeDef(name = "MonetaryAmount", typeClass = MonetaryAmountType.class, defaultForType = MonetaryAmount.class)
package com.totvs.tjc.infra.jpa;

import javax.money.MonetaryAmount;

import org.hibernate.annotations.TypeDef;

import com.totvs.tjc.carteira.ContaId;
import com.totvs.tjc.emprestimo.EmprestimoId;
import com.totvs.tjc.infra.jpa.type.IdCustomTypes;
import com.totvs.tjc.infra.jpa.type.MonetaryAmountType;
