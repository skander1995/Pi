<?php

namespace FaqBundle\Form\Type;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class QuestionType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
        
            ->add('idUsr')
            ->add('datepub')
            ->add('sujet')
            ->add('description')
            ->add('etat')
        ;
    }

    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'FaqBundle\Entity\Question',
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getName()
    {
        return 'question';
    }
}
