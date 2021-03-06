package org.frustra.feather.hooks;

import org.frustra.filament.Hooks;
import org.frustra.filament.hooking.BadHookException;
import org.frustra.filament.hooking.FilamentClassNode;
import org.frustra.filament.hooking.types.HookingPass;
import org.frustra.filament.hooking.types.InstructionProvider;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

@HookingPass(2)
public class ServerPropertiesClass extends InstructionProvider {
	public boolean match(FilamentClassNode node) {
		return node.containsConstant("spawn-protection");
	}

	public boolean match(FilamentClassNode node, MethodNode m) throws BadHookException {
		Type ret = Type.getReturnType(m.desc);
		Type[] args = Type.getArgumentTypes(m.desc);
		return ret == Type.BOOLEAN_TYPE && args.length == 5;
	}

	public boolean match(FilamentClassNode node, MethodNode m, AbstractInsnNode insn) throws BadHookException {
		return insn.getOpcode() == Opcodes.INVOKEINTERFACE && ((MethodInsnNode) insn).name.equals("isEmpty");
	}

	public void complete(FilamentClassNode node, MethodNode m, AbstractInsnNode insn) {
		Hooks.set("ServerProperties", node);
		Hooks.set("ServerProperties.isSpawnProtected", m);
	}
}
