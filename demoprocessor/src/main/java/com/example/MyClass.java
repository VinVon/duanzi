package com.example;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

@AutoService(Processor.class)
public class MyClass extends AbstractProcessor{
    private Elements elementUtils;
    private Filer filer;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementUtils = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }
    /**
     * 参考了 ButterKnife 的写法
     *
     * @return
     */
    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(BindView.class);
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // 调试打印
//        System.out.println("------------------------------------>");
//        Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(BindView.class);
//        Map<Element,List<Element>> analysisElementMap = new LinkedHashMap<>();
//        for (Element element : elementsAnnotatedWith) {
//           //获取包名
//            Element enclosingElement = element.getEnclosingElement();
//            System.out.println("------------------------------------>"+enclosingElement);
//            List<Element> elements = analysisElementMap.get(enclosingElement);
//            if (elements ==null){
//                elements = new ArrayList<>();
//                analysisElementMap.put(enclosingElement,elements);
//            }
//            elements.add(element);
//        }
//        //生成java类
//        for (Map.Entry<Element,List<Element>> entry: analysisElementMap.entrySet()) {
//            Element enclosingElement = entry.getKey();
//            List<Element> elements = entry.getValue();
//            String classNameStr = enclosingElement.getSimpleName().toString();
//            // 组装类:  xxx_ViewBinding implements Unbinder
//            TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(classNameStr + "_ViewBinding")
//                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
//            String packageName = elementUtils.getPackageOf(enclosingElement).getQualifiedName().toString();
//            try {
//
//                JavaFile.builder(packageName,typeSpecBuilder.build())
//                        .addFileComment("自动生成")
//                        .build().writeTo(filer);
//            } catch (IOException e) {
//                e.printStackTrace();
//                System.out.println("翻车了");
//            }
//        }
        generateWXPayCode(roundEnvironment);
        return false;
    }
    private void generateWXPayCode(RoundEnvironment roundEnvironment) {
        MyVisitor visitor = new MyVisitor();
        visitor.setmFiler(filer);
        scanElement(roundEnvironment,BindView.class,visitor);
    }
    private void scanElement(RoundEnvironment roundEnvironment, Class<? extends Annotation> annotation, AnnotationValueVisitor visitor){
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(annotation);
        for (Element element : elements) {
            List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();

            for (AnnotationMirror annotationMirror : annotationMirrors) {
                Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationMirror.getElementValues();

                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry:elementValues.entrySet()){
                    entry.getValue().accept(visitor,null);
                }
            }
        }
    }
}
