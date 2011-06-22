#!/usr/bin/perl

use strict;

my $pgn = {};

sub Line($) {
	my @a = split /\s+/;
	foreach (@a) { s/\d+\.//; }
	return @a;
}

sub Loop($) {
	my ($kirja) = @_;

	my $nro = 0;
	my $tila = 0;
	my $rivi = [];

	open BOOK, $kirja or die $!;

	while (<BOOK>) {
		chomp;

		if ($tila == 0) {
			if (/^\[/ or /^\s*$/) {
				$tila = 0;
			}
			else {
				$nro = $.;
				$tila = 1;
				push @$rivi, Line($_);
			}
		}
		elsif ($tila == 1) {
			if (/^\s*$/) {
				$pgn->{$nro} = $rivi;
				$rivi = [];
				$tila = 0;
			}
			else {
				push @$rivi, Line($_);
			}
		}
	}

	close BOOK;

	foreach my $i (keys %$pgn) {
		print $i." [ ";
		foreach (@{$pgn->{$i}}) {
			print $_." ";
		}
		print "]\n";
	}
}

sub Main() {
	Loop("book.pgn");
	return 0;
}

exit Main;
