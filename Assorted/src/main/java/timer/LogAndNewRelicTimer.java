package timer;

import com.newrelic.api.agent.NewRelic;
import org.slf4j.Logger;

import java.util.function.Supplier;

public class LogAndNewRelicTimer extends LogTimer {

    private final Supplier<String> newRelicMsgSupplier;

    public LogAndNewRelicTimer(Supplier<String> logMsgSupplier, Supplier<String> newRelicParamSupplier) {
        super(logMsgSupplier);
        this.newRelicMsgSupplier = newRelicParamSupplier;
    }

    public LogAndNewRelicTimer(Supplier<String> logMsgSupplier, Supplier<String> newRelicParamSupplier, Logger logger) {
        super(logMsgSupplier, logger);
        this.newRelicMsgSupplier = newRelicParamSupplier;
    }

    @Override
    protected void postProcess() {
        super.postProcess();
        NewRelic.addCustomParameter(newRelicMsgSupplier.get(), duration);
    }
}
