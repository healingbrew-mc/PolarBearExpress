package dyn.healingbrew.polarbearexpress.core;

import net.minecraft.launchwrapper.IClassTransformer;

import static net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import static org.objectweb.asm.Opcodes.*;

import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.util.TraceMethodVisitor;
import org.objectweb.asm.tree.*;
import org.objectweb.asm.util.Printer;
import org.objectweb.asm.util.Textifier;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;

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
    static final SafeName obf_travel = new SafeName("travel", "func_191986_a");
    static final SafeName obf_getJumpUpwardsMotion = new SafeName("getJumpUpwardsMotion", "func_175134_bD");


    private static Printer textifier = new Textifier();
    private static TraceMethodVisitor tmv = new TraceMethodVisitor(textifier);

    public void DumpASM(MethodNode n) {
        for(int i = 0; i < n.instructions.size(); ++i) {
            AbstractInsnNode insn = n.instructions.get(i);
            insn.accept(tmv);
            StringWriter sw = new StringWriter();
            textifier.print(new PrintWriter(sw));
            textifier.getText().clear();
            logger.info(sw.toString().trim());
        }
        logger.info("");
    }

    @Override
    public byte[] transform(String name, String transformedName, byte[] basicClass) {
        if(entityPolarBear_Class.equals(transformedName)) {
            logger.info(String.format("Attempting to transform %s, will dump assembly code", transformedName));

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

                MethodNode n = new MethodNode(ASM5, ACC_PUBLIC,
                        deObf ? obf_canBeSteered.deobf : obf_canBeSteered.obf, "()Z",
                        null, null);

                // return true;
                n.instructions = new InsnList();
                n.instructions.add(new InsnNode(ICONST_1)); // Load true into stack
                n.instructions.add(new InsnNode(IRETURN)); // return stack

                classNode.methods.add(n);

                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
                classNode.accept(cw);

                logger.info(String.format("Tranformed, added %s.%s overriding super (%s)", transformedName, deObf ? obf_canBeSteered.deobf : obf_canBeSteered.obf, obf_canBeSteered.deobf));
                DumpASM(n);
                theBytes = cw.toByteArray();
            }

            // block getJumpUpwardsMotion
            {
                ClassNode classNode = new ClassNode();
                ClassReader classReader = new ClassReader(theBytes);
                classReader.accept(classNode, 0);

                MethodNode n = new MethodNode(ASM5, ACC_PUBLIC,
                        deObf ? obf_getJumpUpwardsMotion.deobf : obf_getJumpUpwardsMotion.obf, "()F",
                        null, null);

                // return 0.84F;
                n.instructions = new InsnList();
                n.instructions.add(new LdcInsnNode(0.58F)); // Load true into stack
                n.instructions.add(new InsnNode(FRETURN)); // return stack

                classNode.methods.add(n);

                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
                classNode.accept(cw);

                logger.info(String.format("Tranformed, added %s.%s overriding super (%s)", transformedName, deObf ? obf_getJumpUpwardsMotion.deobf : obf_getJumpUpwardsMotion.obf, obf_getJumpUpwardsMotion.deobf));
                DumpASM(n);
                theBytes = cw.toByteArray();
            }

            // block getControllingPassenger
            {
                ClassNode classNode = new ClassNode();
                ClassReader classReader = new ClassReader(theBytes);
                classReader.accept(classNode, 0);

                MethodNode n = new MethodNode(ASM5, ACC_PUBLIC,
                        deObf ? obf_getControllingPassenger.deobf : obf_getControllingPassenger.obf, "()Lnet/minecraft/entity/Entity;",
                        null, null);

                n.visibleAnnotations = new ArrayList<>();
                n.visibleAnnotations.add(new AnnotationNode("Ljavax/annotation/Nullable;"));

                n.instructions = new InsnList();

                LabelNode L1 = new LabelNode(new Label());
                LabelNode L2 = new LabelNode(new Label());

                // Entity superEntity = super.getControllingPassenger();
                // retrun super.getControllingPassenger() ?? dyn.healingbrew.polarbearexpress.core.transform.HBMethod.getControllingPassenger(this);
                n.instructions.add(new VarInsnNode(ALOAD, 0)); // load this into ref
                n.instructions.add(new MethodInsnNode(INVOKESPECIAL,
                        "net/minecraft/entity/passive/EntityAnimal",
                        deObf ? obf_getControllingPassenger.deobf : obf_getControllingPassenger.obf,
                        "()Lnet/minecraft/entity/Entity;", false)); // call super.getControllingPassenger
                n.instructions.add(new VarInsnNode(ASTORE, 1)); // save result
                n.instructions.add(new VarInsnNode(ALOAD, 1)); // load result into ref
                n.instructions.add(new JumpInsnNode(IFNONNULL, L1)); // jump to L1 if ref is non null
                n.instructions.add(new VarInsnNode(ALOAD, 0)); // load this into ref
                n.instructions.add(new MethodInsnNode(INVOKESTATIC,
                        "dyn/healingbrew/polarbearexpress/core/HBMethod",
                        "getControllingPassenger",
                        "(Lnet/minecraft/entity/Entity;)Lnet/minecraft/entity/Entity;", false)); // call HBMethod.getControllingPassenger
                n.instructions.add(new JumpInsnNode(GOTO, L2)); // jump to L2
                n.instructions.add(L1);
                n.instructions.add(new VarInsnNode(ALOAD, 1)); // load into ref
                n.instructions.add(L2);
                n.instructions.add(new InsnNode(ARETURN)); // return stack

                classNode.methods.add(n);

                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
                classNode.accept(cw);

                logger.info(String.format("Tranformed, added %s.%s overriding super (%s)", transformedName, deObf ? obf_getControllingPassenger.deobf : obf_getControllingPassenger.obf, obf_getControllingPassenger.deobf));
                DumpASM(n);
                theBytes = cw.toByteArray();
            }

            // block travel-
            {
                ClassNode classNode = new ClassNode();
                ClassReader classReader = new ClassReader(theBytes);
                classReader.accept(classNode, 0);

                MethodNode n = new MethodNode(ASM5, ACC_PUBLIC,
                        deObf ? obf_travel.deobf : obf_travel.obf, "(FFF)V",
                        null, null);

                // if(!HBMethod.travel(this, arg1, arg2, arg3)) { super.travel(arg1, arg2, arg3); }
                n.instructions = new InsnList();

                LabelNode L1 = new LabelNode(new Label());

                n.instructions.add(new VarInsnNode(ALOAD, 0)); // Load this into stack
                n.instructions.add(new VarInsnNode(FLOAD, 1)); // Load arg 1 into stack
                n.instructions.add(new VarInsnNode(FLOAD, 2)); // Load arg 2 into stack
                n.instructions.add(new VarInsnNode(FLOAD, 3)); // Load arg 3 into stack
                n.instructions.add(new MethodInsnNode(INVOKESTATIC,
                        "dyn/healingbrew/polarbearexpress/core/HBMethod",
                        "travel",
                        "(Lnet/minecraft/entity/Entity;FFF)[F", false)); // call GBMethod.getControllingPassenger
                n.instructions.add(new VarInsnNode(ASTORE, 4)); // Load result into reg
                n.instructions.add(new VarInsnNode(ALOAD, 0));  // Store this into stack
                n.instructions.add(new VarInsnNode(ALOAD, 4));  // Store result into stack
                n.instructions.add(new InsnNode(ICONST_0));          // Load index 0 into array
                n.instructions.add(new InsnNode(FALOAD));            // Store value into stack
                n.instructions.add(new VarInsnNode(ALOAD, 4));  // Store result into stack
                n.instructions.add(new InsnNode(ICONST_1));          // Load index 1 into array
                n.instructions.add(new InsnNode(FALOAD));            // Store value into stack
                n.instructions.add(new VarInsnNode(ALOAD, 4));  // Store result into stack
                n.instructions.add(new InsnNode(ICONST_2));          // Load index 2 into array
                n.instructions.add(new InsnNode(FALOAD));            // Store value into stack
                n.instructions.add(new MethodInsnNode(INVOKESPECIAL,
                        "net/minecraft/entity/passive/EntityAnimal",
                        deObf ? obf_travel.deobf : obf_travel.obf,
                        "(FFF)V", false)); // call super.travel
                n.instructions.add(L1);
                n.instructions.add(new InsnNode(RETURN)); // return

                classNode.methods.add(n);

                ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
                classNode.accept(cw);

                logger.info(String.format("Tranformed, added %s.%s overriding super (%s)", transformedName, deObf ? obf_travel.deobf : obf_travel.obf, obf_travel.deobf));
                DumpASM(n);
                theBytes = cw.toByteArray();
            }

            logger.info("All done!");

            return theBytes;
        }
        return basicClass;
    }
}
