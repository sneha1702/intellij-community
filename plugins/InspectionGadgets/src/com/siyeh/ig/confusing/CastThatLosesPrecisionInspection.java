package com.siyeh.ig.confusing;

import com.intellij.codeInspection.InspectionManager;
import com.intellij.psi.*;
import com.siyeh.ig.*;
import com.siyeh.ig.psiutils.ClassUtils;

import java.util.HashMap;
import java.util.Map;

public class CastThatLosesPrecisionInspection extends ExpressionInspection {
    /** @noinspection StaticCollection*/
    private static final Map s_typePrecisions = new HashMap(7);

    static {
        s_typePrecisions.put(PsiType.BYTE, new Integer(1));
        s_typePrecisions.put(PsiType.CHAR, new Integer(2));
        s_typePrecisions.put(PsiType.SHORT, new Integer(2));
        s_typePrecisions.put(PsiType.INT, new Integer(3));
        s_typePrecisions.put(PsiType.LONG, new Integer(4));
        s_typePrecisions.put(PsiType.FLOAT, new Integer(5));
        s_typePrecisions.put(PsiType.DOUBLE, new Integer(6));
    }

    public String getID(){
        return "NumericCastThatLosesPrecision";
    }
    public String getDisplayName() {
        return "Numeric cast that loses precision";
    }

    public String getGroupDisplayName() {
        return GroupNames.CONFUSING_GROUP_NAME;
    }

    public String buildErrorString(PsiElement location) {
        final PsiTypeCastExpression expression = (PsiTypeCastExpression) location.getParent();
        final PsiExpression operand = expression.getOperand();
        final PsiType operandType = operand.getType();
        return "Cast to #ref from " + operandType.getPresentableText() + " may result in loss of precision #loc";
    }

    public BaseInspectionVisitor createVisitor(InspectionManager inspectionManager, boolean onTheFly) {
        return new CastThatLosesPrecisionVisitor(this, inspectionManager, onTheFly);
    }

    private static class CastThatLosesPrecisionVisitor extends BaseInspectionVisitor {
        private CastThatLosesPrecisionVisitor(BaseInspection inspection, InspectionManager inspectionManager, boolean isOnTheFly) {
            super(inspection, inspectionManager, isOnTheFly);
        }

        public void visitTypeCastExpression(PsiTypeCastExpression exp) {
            final PsiType castType = exp.getType();
            if (!ClassUtils.isPrimitiveNumericType(castType)) {
                return;
            }

            final PsiExpression operand = exp.getOperand();

            final PsiType operandType = operand.getType();
            if (!ClassUtils.isPrimitiveNumericType(operandType)) {
                return;
            }

            if (hasLowerPrecision(operandType, castType)) {
                return;
            }
            final PsiTypeElement castTypeElement = exp.getCastType();
            registerError(castTypeElement);
        }

        private static boolean hasLowerPrecision(PsiType operandType, PsiType castType) {
            final Integer operandPrecision = (Integer) s_typePrecisions.get(operandType);
            final Integer castPrecision = (Integer) s_typePrecisions.get(castType);
            return operandPrecision.intValue() <= castPrecision.intValue();
        }

    }

}
