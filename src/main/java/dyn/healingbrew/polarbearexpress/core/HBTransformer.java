package dyn.healingbrew.polarbearexpress.core;

import net.minecraft.launchwrapper.IClassTransformer;

import static net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import static org.objectweb.asm.Opcodes.*;

import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Label;
import org.objectweb.asm.tree.*;

@SuppressWarnings("unused WeakerAccess")
@MCVersion(MinecraftForge.MC_VERSION)
public class HBTransformer implements IClassTransformer {
    public static final Logger logger = LogManager.getLogger("healingbrew.asm");

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    protected static class SafeName {
        final String deobf;
        final String obf;

        public SafeName(String deobf, String obf) {
            this.deobf = deobf;
            this.obf = obf;
        }

        public String getName() {
            return HBPlugin.Deobf ? obf : deobf;
        }

        @Override
        public boolean equals(Object obj) {
            return obf.hashCode() == obj.hashCode() || deobf.hashCode() == obj.hashCode();
        }

        @Override
        public int hashCode() {
            return this.getName().hashCode();
        }
    }

    static final String entityPolarBear_Class = "net.minecraft.entity.monster.EntityPolarBear";
    static final SafeName obf_getControllingPassenger = new SafeName("getControllingPassenger", "func_184179_bs");
    static final SafeName obf_canBeSteered = new SafeName("canBeSteered", "func_82171_bF");

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if(entityPolarBear_Class.equals(transformedName)) {
            logger.info(String.format("Attempting to transform %s", transformedName));

            ClassNode classNode = new ClassNode();
            ClassReader classReader = new ClassReader(basicClass);
            classReader.accept(classNode, 0);

            boolean deObf = false;
            for(MethodNode method : classNode.methods) {
                if(method.name.equals("onUpdate")) {
                    logger.info("Not obfuscated");
                    deObf = true;
                    break;
                }
            }

            // block canBeSteered
            {
                MethodNode n = new MethodNode(ASM4, ACC_PUBLIC,
                        deObf ? obf_canBeSteered.deobf : obf_canBeSteered.obf, "()Z",
                        null, null);

                // return true;
                n.instructions = new InsnList();
                n.instructions.add(new InsnNode(ICONST_1)); // Load true into stack
                n.instructions.add(new InsnNode(IRETURN)); // return stack

                classNode.methods.add(n);
                logger.info(String.format("Tranformed, added %s.%s overriding super", transformedName, obf_canBeSteered.deobf));
            }

            // block getControllingPassenger
            {
                MethodNode n = new MethodNode(ASM4, ACC_PUBLIC,
                        deObf ? obf_getControllingPassenger.deobf : obf_getControllingPassenger.obf, "()Z",
                        null, null);

                n.instructions = new InsnList();

                LabelNode L1 = new LabelNode(new Label());
                LabelNode L2 = new LabelNode(new Label());

                // Entity superEntity = super.getControllingPassenger();
                // retrun superEntity != null ? superEntity :
                //                  dyn.healingbrew.polarbearexpress.core.transform.HBMethod.getControllingPassenger(this);
                n.instructions.add(new VarInsnNode(ALOAD, 0)); // load stack into ref
                n.instructions.add(new MethodInsnNode(INVOKESPECIAL,
                        "net/minecraft/entity/passive/EntityAnimal",
                        deObf ? obf_getControllingPassenger.deobf : obf_getControllingPassenger.obf,
                        "()Z", false)); // call super.getControllingPassenger();
                n.instructions.add(new VarInsnNode(ASTORE, 1)); // store result in stack
                n.instructions.add(new VarInsnNode(ALOAD, 1)); // load stack into ref
                n.instructions.add(new JumpInsnNode(IFNONNULL, L1)); // jump to L1 if not null
                n.instructions.add(new VarInsnNode(ALOAD, 0)); // load this into ref
                n.instructions.add(new MethodInsnNode(INVOKESTATIC,
                        "dyn/healingbrew/polarbearexpress/core/transform/HBMethod",
                        "getControllingPassenger",
                        "(Lnet/minecraft/entity/Entity;)Z", false)); // call GBMethod.getControllingPassenger
                n.instructions.add(new JumpInsnNode(GOTO, L2)); // jump to L2
                n.instructions.add(L1); // L1 marker
                n.instructions.add(new VarInsnNode(ALOAD, 1)); // load stack 1 into ref
                n.instructions.add(L2); // 12 marker
                n.instructions.add(new InsnNode(ARETURN)); // return stack

                classNode.methods.add(n);
                logger.info(String.format("Tranformed, added %s.%s overriding super", transformedName, obf_getControllingPassenger.deobf));
            }
            logger.info(String.format("Transformed %s", transformedName));
        }
        return basicClass;
    }
}
