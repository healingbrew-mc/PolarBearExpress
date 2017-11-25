package dyn.healingbrew.polarbearexpress.core;

import com.sun.org.apache.bcel.internal.generic.ICONST;
import net.minecraft.launchwrapper.IClassTransformer;

import static net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import static org.objectweb.asm.Opcodes.*;

import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
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

            byte[] theBytes = basicClass;

            boolean deObf = false;

            {
                ClassNode classNode = new ClassNode();
                ClassReader classReader = new ClassReader(theBytes);
                classReader.accept(classNode, 0);

                for (MethodNode method : classNode.methods) {
                    if (method.name.equals("onUpdate")) {
                        logger.info("Not obfuscated");
                        deObf = true;
                        break;
                    }
                }
            }

            // block canBeSteered
            {
                ClassNode classNode = new ClassNode();
                ClassReader classReader = new ClassReader(theBytes);
                classReader.accept(classNode, 0);

                MethodNode n = new MethodNode(ASM4, ACC_PUBLIC,
                        deObf ? obf_canBeSteered.deobf : obf_canBeSteered.obf, "()Z",
                        null, null);

                // return true;
                n.instructions = new InsnList();
                n.instructions.add(new InsnNode(ICONST_1)); // Load true into stack
                n.instructions.add(new InsnNode(IRETURN)); // return stack

                classNode.methods.add(n);

                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                classNode.accept(cw);

                logger.info(String.format("Tranformed, added %s.%s overriding super (%s)", transformedName, deObf ? obf_canBeSteered.deobf : obf_canBeSteered.obf, obf_canBeSteered.deobf));
                theBytes = cw.toByteArray();
            }

            // block getControllingPassenger
            {
                ClassNode classNode = new ClassNode();
                ClassReader classReader = new ClassReader(theBytes);
                classReader.accept(classNode, 0);

                MethodNode n = new MethodNode(ASM4, ACC_PUBLIC,
                        deObf ? obf_getControllingPassenger.deobf : obf_getControllingPassenger.obf, "()Z",
                        null, null);

                n.instructions = new InsnList();

                LabelNode L1 = new LabelNode(new Label());
                LabelNode L2 = new LabelNode(new Label());

                // Entity superEntity = super.getControllingPassenger();
                // retrun dyn.healingbrew.polarbearexpress.core.transform.HBMethod.getControllingPassenger(this);
                n.instructions.add(new VarInsnNode(ALOAD, 0)); // load this into ref
                n.instructions.add(new MethodInsnNode(INVOKESTATIC,
                        "dyn/healingbrew/polarbearexpress/core/transform/HBMethod",
                        "getControllingPassenger",
                        "(Lnet/minecraft/entity/Entity;)Z", false)); // call GBMethod.getControllingPassenger
                n.instructions.add(new InsnNode(IRETURN)); // return stack

                classNode.methods.add(n);

                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                classNode.accept(cw);

                logger.info(String.format("Tranformed, added %s.%s overriding super (%s)", transformedName, deObf ? obf_getControllingPassenger.deobf : obf_getControllingPassenger.obf, obf_getControllingPassenger.deobf));

                theBytes = cw.toByteArray();
            }

            return theBytes;
        }
        return basicClass;
    }
}
