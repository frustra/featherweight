package org.frustra.feather.hooks;

import org.frustra.filament.hooking.CustomClassNode;
import org.frustra.filament.hooking.Hooks;
import org.frustra.filament.hooking.types.HookingPassOne;
import org.frustra.filament.hooking.types.MethodHook;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class PlayerSocketHandlerClass extends MethodHook implements HookingPassOne {
	public boolean match(CustomClassNode node) {
		return node.constants.contains("multiplayer.player.left");
	}

	protected boolean match(CustomClassNode node, MethodNode m) {
		AbstractInsnNode insn = m.instructions.getFirst();
		while (insn != null) {
			if (insn instanceof LdcInsnNode) {
				if (((LdcInsnNode) insn).cst.toString().equals("multiplayer.player.left")) return true;
			}
			insn = insn.getNext();
		}
		return false;
	}

	public void onComplete(CustomClassNode node, MethodNode m) {
		Hooks.set("PlayerSocketHandler", node);
		Hooks.set("PlayerSocketHandler.playerLeft", m);
	}
}
