package org.frustra.feather.hooks;

import org.frustra.filament.Hooks;
import org.frustra.filament.hooking.BadHookException;
import org.frustra.filament.hooking.FilamentClassNode;
import org.frustra.filament.hooking.types.HookingPassTwo;
import org.frustra.filament.hooking.types.MethodHook;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.MethodNode;

public class HandleExecuteCommandMethod extends MethodHook implements HookingPassTwo {
	public boolean match(FilamentClassNode node) throws BadHookException {
		return node.matches("BaseCommand");
	}

	public boolean match(FilamentClassNode node, MethodNode m) throws BadHookException {
		Type[] args = new Type[] { Type.getObjectType(Hooks.getClassName("CommandEntity")), Type.getType(String[].class) };
		return m.desc.equals(Type.getMethodDescriptor(Type.VOID_TYPE, args));
	}

	public void onComplete(FilamentClassNode node, MethodNode m) {
		Hooks.set("Command.handleExecute", m);
	}
}
