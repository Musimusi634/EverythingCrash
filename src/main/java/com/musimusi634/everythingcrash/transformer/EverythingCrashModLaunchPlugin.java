package com.musimusi634.everythingcrash.transformer;

import cpw.mods.modlauncher.api.ITransformerActivity;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

import java.util.EnumSet;

public class EverythingCrashModLaunchPlugin implements ILaunchPluginService {

    @Override
    public String name() {
        return "everythingcrash_launch_plugin";
    }

    @Override
    public int processClassWithFlags(Phase phase, ClassNode classNode, Type classType, String reason) {
        if (!reason.equals(ITransformerActivity.CLASSLOADING_REASON)) return ComputeFlags.NO_REWRITE;
        return GenericTransformer.transform(classNode);
    }

    @Override
    public EnumSet<Phase> handlesClass(Type classType, boolean isEmpty) {
        return EnumSet.of(Phase.BEFORE,Phase.AFTER);
    }
}
