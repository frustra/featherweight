package org.frustra.feather.hooks;

import org.frustra.filament.hooking.BadHookException;
import org.frustra.filament.hooking.CustomClassNode;
import org.frustra.filament.hooking.Hooks;
import org.frustra.filament.hooking.types.HookingPassTwo;
import org.frustra.filament.hooking.types.MethodHook;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.MethodNode;

public class HasCommandPermissionMethod extends MethodHook implements HookingPassTwo {
	public boolean match(CustomClassNode node) throws BadHookException {
		return node.matches("BaseCommand");
	}

	public boolean match(CustomClassNode node, MethodNode m) throws BadHookException {
		Type[] args = new Type[] { Type.getObjectType(Hooks.getClassName("CommandEntity")) };
		return m.desc.equals(Type.getMethodDescriptor(Type.BOOLEAN_TYPE, args));
	}

	public void onComplete(CustomClassNode node, MethodNode m) {
		Hooks.set("Command.hasPermission", m);
	}
}
