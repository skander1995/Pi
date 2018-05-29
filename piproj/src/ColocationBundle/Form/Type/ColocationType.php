<?php

namespace ColocationBundle\Form\Type;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;

class ColocationType extends AbstractType
{
    /**
     * {@inheritdoc}
     */
    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
        
            ->add('idUsr')
            ->add('datepub')
            ->add('description')
            ->add('etat')
            ->add('lieu')
            ->add('loyermensuel')
            ->add('typelogement')
            ->add('typechambre')
            ->add('imgcouverture')
            ->add('nbchambre')
            ->add('commodite')
            ->add('datedisponibilite')
            ->add('idVille')
        ;
    }

    /**
     * {@inheritdoc}
     */
    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults(array(
            'data_class' => 'ColocationBundle\Entity\Colocation',
        ));
    }

    /**
     * {@inheritdoc}
     */
    public function getName()
    {
        return 'colocation';
    }
}
