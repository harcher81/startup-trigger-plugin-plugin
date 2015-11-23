package org.jvnet.hudson.plugins.triggers.startup;

import antlr.ANTLRException;
import hudson.Extension;
import hudson.Util;
import hudson.model.BuildableItem;
import hudson.model.Item;
import hudson.triggers.Trigger;
import hudson.triggers.TriggerDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;

/**
 * Enables the current job to be restarted when Jenkins nodes start
 * (The user is able to set a label to restrict some Jenkins nodes).
 * Without any specified label, the job is restarted when the master Jenkins
 * instances starts
 *
 * @author Ash Lux
 * @author Gregory Boissinot
 */
public class HudsonStartupTrigger extends Trigger<BuildableItem> {

    private String label;
    private int quietPeriod;
    private boolean runOnNewSlave;

    public HudsonStartupTrigger(String label, String quietPeriod) throws ANTLRException {
        this(label, quietPeriod, false);
    }

    @DataBoundConstructor
    public HudsonStartupTrigger(String label, String quietPeriod, boolean runOnNewSlave) throws ANTLRException {
        super();
        this.label = Util.fixEmpty(label);
        String givenQuietPeriod = Util.fixEmpty(quietPeriod);
        if (givenQuietPeriod == null) {
            this.quietPeriod = 0;
        } else {
            this.quietPeriod = Integer.parseInt(quietPeriod);
        }

        this.runOnNewSlave = runOnNewSlave;
    }

    public String getLabel() {
        return label;
    }

    public int getQuietPeriod() {
        return quietPeriod;
    }

    public boolean getRunOnNewSlave() {
        return runOnNewSlave;
    }

    @Extension
    public static class HudsonStartupDescriptor extends TriggerDescriptor {

        @Override
        public boolean isApplicable(Item item) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "Build when job nodes start";
        }
    }
}
