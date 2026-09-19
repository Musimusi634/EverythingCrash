package com.musimusi634.everythingcrash.transformer;

import cpw.mods.modlauncher.LaunchPluginHandler;
import cpw.mods.modlauncher.Launcher;
import cpw.mods.modlauncher.serviceapi.ILaunchPluginService;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.lang.reflect.Field;
import java.util.Map;

public class GenericTransformer {

    static boolean initialized = false;

    public static int transform(ClassNode classNode) {
        for (MethodNode method : classNode.methods) {
            for (AbstractInsnNode Insn : method.instructions) {
                InsnList instructions = new InsnList();
                instructions.add(new MethodInsnNode(
                        Opcodes.INVOKESTATIC,
                        "com/musimusi634/everythingcrash/transformer/Methods",
                        "crash",
                        "()V",
                        false
                ));
                method.instructions.insert(instructions);
                break;
            }
        }
        return ILaunchPluginService.ComputeFlags.SIMPLE_REWRITE;
    }

    //All code below is from https://github.com/kosianodanngoo/TheTrialMonolith/blob/master/src/main/java/io/github/kosianodangoo/trialmonolith/transformer/GenericTransformer.java
    public static void initialize() {
        if (initialized) return;

        try {
            ILaunchPluginService plugin = new EverythingCrashModLaunchPlugin();

            Field field = Launcher.class.getDeclaredField("launchPlugins");
            field.setAccessible(true);
            LaunchPluginHandler pluginHandler = (LaunchPluginHandler) field.get(Launcher.INSTANCE);
            field = LaunchPluginHandler.class.getDeclaredField("plugins");
            field.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<String, ILaunchPluginService> map = (Map<String, ILaunchPluginService>) field.get(pluginHandler);
            map.put(plugin.name(), plugin);
        } catch (NoSuchFieldException | IllegalAccessException e) {;
        }
        initialized = true;
    }
}